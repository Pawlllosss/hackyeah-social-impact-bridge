package pl.hackyeah.backend.entity

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import java.time.Instant
import java.util.*


@DynamoDBTable(tableName = "corporations")
@Serializable
data class CorporationSearchableObject(
    @SerializedName("objectID") val corporateId: String,
    val name: String,
    @SerializedName("created_at") val createdAt: Long,
    @SerializedName("updated_at") val updatedAt: Long,
    val desc: String,
    val categories: List<String>,
    val deadline: Long,
    val city: String,
    val image: String,
    @SerializedName("min_budget") val minBudget: Long,
    @SerializedName("max_budget") val maxBudget: Long,
    @SerializedName("_geoloc") val geoLocation: List<GeoLocation>,

    ) {

    companion object {
        fun fromCorporation(corporation: Corporation): CorporationSearchableObject =
            CorporationSearchableObject(
                corporation.corporateId,
                corporation.name,
                corporation.createdAt.epochSecond,
                corporation.updatedAt.epochSecond,
                corporation.desc,
                corporation.categories,
                corporation.deadline.epochSecond,
                corporation.city,
                corporation.image,
                corporation.minBudget,
                corporation.maxBudget,
                corporation.geoLocation
            )
    }
}