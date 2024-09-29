package pl.hackyeah.backend.entity

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import java.time.Instant


@DynamoDBTable(tableName = "corporations")
@Serializable
data class Corporation(
    @SerializedName("objectID") val corporateId: String,
    val name: String,
    @SerializedName("created_at") @Serializable(with = InstantSerializer::class) val createdAt: Instant = Instant.now(),
    @SerializedName("updated_at") @Serializable(with = InstantSerializer::class) val updatedAt: Instant = Instant.now(),
    val desc: String,
    val categories: List<String>,
    @Serializable(with = InstantSerializer::class) val deadline: Instant,
    val city: String,
    val krs: String,
    val email: String,
    val phone: String,
    val website: String,
    val image: String,
    @SerializedName("min_budget") val minBudget: Long,
    @SerializedName("max_budget") val maxBudget: Long,
    @SerializedName("_geoloc") val geoLocation: List<GeoLocation>,

    ) {

    companion object {
        const val DYNAMO_DB_PRIMARY_KEY = "corporate_id"
        private const val NAME = "name"
        private const val CREATED_AT = "created_at"
        private const val UPDATED_AT = "updated_at"
        private const val DEADLINE = "deadline"
        private const val DESC = "desc"
        private const val CATEGORIES = "categories"
        private const val CITY = "city"
        private const val KRS = "KRS"
        private const val EMAIL = "email"
        private const val PHONE = "phone"
        private const val WEBSITE = "website"
        private const val IMAGE = "image"
        private const val MIN_BUDGET = "min_budget"
        private const val MAX_BUDGET = "max_budget"
        private const val GEOLOC = "geoloc"

        private val OBJECT_MAPPER = jacksonObjectMapper()

        fun fromDynamoDbResult(dynamoDbResult: Map<String, AttributeValue>): Corporation {
            val createdAt = dynamoDbResult[CREATED_AT]!!.s
            val updatedAt = dynamoDbResult[UPDATED_AT]!!.s
            val deadline = dynamoDbResult[DEADLINE]!!.s
            val categories = dynamoDbResult[CATEGORIES]!!.l.mapNotNull { it.s }
            val geoLoc = OBJECT_MAPPER.readValue<List<GeoLocation>>(dynamoDbResult[GEOLOC]!!.s)

            return Corporation(
                dynamoDbResult[DYNAMO_DB_PRIMARY_KEY]!!.s,
                dynamoDbResult[NAME]!!.s,
                Instant.parse(createdAt),
                Instant.parse(updatedAt),
                dynamoDbResult[DESC]!!.s,
                categories,
                Instant.parse(deadline),
                dynamoDbResult[CITY]!!.s,
                dynamoDbResult[KRS]!!.s,
                dynamoDbResult[EMAIL]!!.s,
                dynamoDbResult[PHONE]!!.s,
                dynamoDbResult[WEBSITE]!!.s,
                dynamoDbResult[IMAGE]!!.s,
                dynamoDbResult[MIN_BUDGET]!!.n.toLong(),
                dynamoDbResult[MAX_BUDGET]!!.n.toLong(),
                geoLoc
            )
        }
    }

    fun toMap(): Map<String, AttributeValue> {
        return mapOf(
            DYNAMO_DB_PRIMARY_KEY to AttributeValue(corporateId),
            NAME to AttributeValue(name),
            CREATED_AT to AttributeValue(createdAt.toString()),
            UPDATED_AT to AttributeValue(updatedAt.toString()),
            DEADLINE to AttributeValue(deadline.toString()),
            DESC to AttributeValue(desc),
            CATEGORIES to AttributeValue().withL(categories.map { AttributeValue(it) }),
            CITY to AttributeValue(city),
            KRS to AttributeValue(krs),
            WEBSITE to AttributeValue(website),
            EMAIL to AttributeValue(email),
            PHONE to AttributeValue(phone),
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