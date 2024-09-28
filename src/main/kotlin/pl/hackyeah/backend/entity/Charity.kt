package pl.hackyeah.backend.entity

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.Instant
import java.util.*

object UUIDSerializer : KSerializer<UUID> {
    override val descriptor = PrimitiveSerialDescriptor("UUID", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): UUID {
        return UUID.fromString(decoder.decodeString())
    }

    override fun serialize(encoder: Encoder, value: UUID) {
        encoder.encodeString(value.toString())
    }
}

object InstantSerializer : KSerializer<Instant> {
    // Describe the serialized form, which is a string (ISO-8601 representation of the Instant)
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("Instant", PrimitiveKind.STRING)

    // Serialize the Instant to an ISO-8601 string
    override fun serialize(encoder: Encoder, value: Instant) {
        encoder.encodeString(value.toString()) // Instant's toString() outputs ISO-8601
    }

    // Deserialize an ISO-8601 string back to an Instant
    override fun deserialize(decoder: Decoder): Instant {
        return Instant.parse(decoder.decodeString()) // Parse ISO-8601 string back to Instant
    }
}


private const val s = "name"

@DynamoDBTable(tableName = "corporations")
@Serializable
data class Charity(
    @SerializedName("objectID") val charityId: String,
    val name: String,
    @SerializedName("created_at") @Serializable(with = InstantSerializer::class) val createdAt: Instant = Instant.now(),
    @SerializedName("updated_at") @Serializable(with = InstantSerializer::class) val updatedAt: Instant = Instant.now(),
    @Serializable(with = InstantSerializer::class) val available: Instant,
    val desc: String,
    val categories: List<String>,
    val city: String,
    val image: String,
    @SerializedName("min_budget") val minBudget: Long,
    @SerializedName("max_budget") val maxBudget: Long,
    @SerializedName("_geoloc") val geoLocation: List<GeoLocation>,

    ) {

    companion object {
        const val DYNAMO_DB_PRIMARY_KEY = "charity_id"
        private const val NAME = "name"
        private const val CREATED_AT = "created_at"
        private const val UPDATED_AT = "updated_at"
        private const val AVAILABLE = "available"
        private const val DESC = "desc"
        private const val CATEGORIES = "categories"
        private const val CITY = "city"
        private const val IMAGE = "image"
        private const val MIN_BUDGET = "min_budget"
        private const val MAX_BUDGET = "max_budget"
        private const val GEOLOC = "geoloc"

        private val OBJECT_MAPPER = jacksonObjectMapper()

        fun fromDynamoDbResult(dynamoDbResult: Map<String, AttributeValue>): Charity {
            val createdAt = dynamoDbResult[CREATED_AT]!!.s
            val updatedAt = dynamoDbResult[UPDATED_AT]!!.s
            val available = dynamoDbResult[AVAILABLE]!!.s
            val categories = dynamoDbResult[CATEGORIES]!!.l
                .map { it.s }
                .filterNotNull()
            val geoLoc = OBJECT_MAPPER.readValue<List<GeoLocation>>(dynamoDbResult[GEOLOC]!!.s)

            return Charity(
                dynamoDbResult[DYNAMO_DB_PRIMARY_KEY]!!.s,
                dynamoDbResult[NAME]!!.s,
                Instant.parse(createdAt),
                Instant.parse(updatedAt),
                Instant.parse(available),
                dynamoDbResult[DESC]!!.s,
                categories,
                dynamoDbResult[CITY]!!.s,
                dynamoDbResult[IMAGE]!!.s,
                dynamoDbResult[MIN_BUDGET]!!.n.toLong(),
                dynamoDbResult[MAX_BUDGET]!!.n.toLong(),
                geoLoc
            )
        }
    }

    fun toMap(): Map<String, AttributeValue> {
        return mapOf(
            DYNAMO_DB_PRIMARY_KEY to AttributeValue(charityId),
            NAME to AttributeValue(name),
            CREATED_AT to AttributeValue(createdAt.toString()),
            UPDATED_AT to AttributeValue(updatedAt.toString()),
            AVAILABLE to AttributeValue(available.toString()),
            DESC to AttributeValue(desc),
            CATEGORIES to AttributeValue().withL(categories.map { AttributeValue(it) }),
            CITY to AttributeValue(city),
            IMAGE to AttributeValue(image),
            MIN_BUDGET to AttributeValue().withN(minBudget.toString()),
            MAX_BUDGET to AttributeValue().withN(maxBudget.toString()),
            GEOLOC to AttributeValue(OBJECT_MAPPER.writeValueAsString(geoLocation))
        )
    }
}

//{
//    objectID: id,
//    uuid: uuid,
//    name: faker.company.buzzPhrase(),
//    created_at: created_at,
//    desc: faker.lorem.paragraph(2),
//    categories: getRandomKeys,
//    deadline: faker.date.future(),
//
//    city: faker.location.city(),
//    image: images[randomImageIndex],
//    min_budget: min_budget,
//    max_budget: min_budget*2,
//    _geoloc: _geoloc,
//
//    updated_at: updated_at,
//
//};