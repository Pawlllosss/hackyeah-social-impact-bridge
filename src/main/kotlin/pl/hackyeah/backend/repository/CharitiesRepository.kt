package pl.hackyeah.backend.repository

import com.algolia.client.api.SearchClient
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.amazonaws.services.dynamodbv2.model.GetItemRequest
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.encodeToJsonElement
import org.springframework.stereotype.Repository
import pl.hackyeah.backend.entity.Charity
import pl.hackyeah.backend.entity.CharitySearchableObject

@Repository
class CharitiesRepository(
    private val dynamoDb: AmazonDynamoDB,
    private val searchClient: SearchClient,
) {

    companion object {
        private const val CHARITIES_INDEX = "charities"
        private const val CHARITY_TABLE = "charities"
    }

    fun get(charityId: String): Charity? {
        val partitionKey = mapOf(Charity.DYNAMO_DB_PRIMARY_KEY to AttributeValue(charityId))
        val dynamoDbResult = dynamoDb.getItem(
            GetItemRequest(CHARITY_TABLE, partitionKey)
        )

        return dynamoDbResult.item?.let { Charity.fromDynamoDbResult(it) }
    }

    fun save(charity: Charity): String {
        val itemValues = charity.toMap()
        val result = dynamoDb.putItem(CHARITY_TABLE, itemValues)

        val charitySearchableObject = CharitySearchableObject.fromCharity(charity)
        val jsonObject = getAsJsonObject(charitySearchableObject)

        runBlocking {
            searchClient.saveObject(CHARITIES_INDEX, jsonObject)
        }

        return charity.charityId
    }

    private fun getAsJsonObject(charity: CharitySearchableObject): JsonObject {
        val jsonElement = Json.encodeToJsonElement(charity)
        return jsonElement as JsonObject
    }
}
