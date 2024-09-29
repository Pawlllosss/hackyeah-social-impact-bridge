package pl.hackyeah.backend.entity

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
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
    val budget: Long,
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
                corporation.budget,
                corporation.geoLocation
            )
    }
}