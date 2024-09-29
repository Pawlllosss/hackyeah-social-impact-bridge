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

@DynamoDBTable(tableName = "corporations")
@Serializable
data class CharitySearchableObject(
    @SerializedName("objectID") val charityId: String,
    val name: String,
    @SerializedName("created_at") val createdAt: Long,
    @SerializedName("updated_at") val updatedAt: Long,
    val available: Long,
    val desc: String,
    val categories: List<String>,
    val city: String,
    val image: String,
    @SerializedName("min_budget") val minBudget: Long,
    @SerializedName("max_budget") val maxBudget: Long,
    @SerializedName("_geoloc") val geoLocation: List<GeoLocation>,

    ) {

    companion object {
        fun fromCharity(charity: Charity): CharitySearchableObject = CharitySearchableObject(
            charity.charityId,
            charity.name,
            charity.createdAt.epochSecond,
            charity.updatedAt.epochSecond,
            charity.available.epochSecond,
            charity.desc,
            charity.categories,
            charity.city,
            charity.image,
            charity.minBudget,
            charity.maxBudget,
            charity.geoLocation
        )
    }
}