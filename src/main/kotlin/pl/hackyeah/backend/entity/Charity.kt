package pl.hackyeah.backend.entity

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable
import com.amazonaws.services.dynamodbv2.model.AttributeValue
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


@DynamoDBTable(tableName = "corporations")
@Serializable
data class Charity(
    @SerializedName("objectID") @Serializable(with = UUIDSerializer::class) val charityId: UUID = UUID.randomUUID(),
    val name: String,
    @SerializedName("created_at") @Serializable(with = InstantSerializer::class) val createdAt: Instant = Instant.now(),
    @SerializedName("updated_at") @Serializable(with = InstantSerializer::class) val updatedAt: Instant = Instant.now(),
    @Serializable(with = InstantSerializer::class) val available: Instant,
    val desc: String,
    val categories: Set<String>,
    val city: String,
    val image: String,
    @SerializedName("min_budget") val minBudget: Long,
    @SerializedName("max_budget") val maxBudget: Long,
    @SerializedName("_geoloc") val geoLocation: List<GeoLocation>,

    ) {
    fun toMap(): Map<String, AttributeValue> {
        return mapOf(
            "charity_id" to AttributeValue(charityId.toString()),
            "name" to AttributeValue(name),
            "created_at" to AttributeValue(createdAt.toString()),
            "updated_at" to AttributeValue(updatedAt.toString()),
            "available" to AttributeValue(available.toString()),
            "desc" to AttributeValue(desc),
            "categories" to AttributeValue(categories.toString()),
            "city" to AttributeValue(city),
            "image" to AttributeValue(image),
            "min_budget" to AttributeValue(minBudget.toString()),
            "max_budget" to AttributeValue(maxBudget.toString()),
            "geoloc" to AttributeValue(geoLocation.toString())
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