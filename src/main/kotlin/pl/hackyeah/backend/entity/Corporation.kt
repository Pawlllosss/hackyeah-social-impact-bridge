package pl.hackyeah.backend.entity

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import java.time.Instant
import java.util.*


@DynamoDBTable(tableName = "corporations")
@Serializable
data class Corporation(
    @SerializedName("objectID") @Serializable(with = UUIDSerializer::class) val corporateId: UUID = UUID.randomUUID(),
    val name: String,
    @SerializedName("created_at") @Serializable(with = InstantSerializer::class) val createdAt: Instant = Instant.now(),
    @SerializedName("updated_at") @Serializable(with = InstantSerializer::class) val updatedAt: Instant = Instant.now(),
    val desc: String,
    val categories: Set<String>,
    @Serializable(with = InstantSerializer::class) val deadline: Instant,
    val city: String,
    val image: String,
    @SerializedName("min_budget") val minBudget: Long,
    @SerializedName("max_budget") val maxBudget: Long,
    @SerializedName("_geoloc") val geoLocation: List<GeoLocation>,

    ) {
    fun toMap(): Map<String, AttributeValue> {
        return mapOf(
            "corporate_id" to AttributeValue(corporateId.toString()),
            "name" to AttributeValue(name),
            "created_at" to AttributeValue(createdAt.toString()),
            "updated_at" to AttributeValue(updatedAt.toString()),
            "deadline" to AttributeValue(deadline.toString()),
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