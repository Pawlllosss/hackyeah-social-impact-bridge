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
import pl.hackyeah.backend.entity.Corporation
import pl.hackyeah.backend.entity.CorporationSearchableObject

@Repository
class CorporationRepository(
    private val dynamoDb: AmazonDynamoDB,
    private val searchClient: SearchClient,
) {

    companion object {
        private const val CORPORATIONS_INDEX = "corporations"
        private const val CORPORATIONS_TABLE = "corporations"
    }

    fun get(corporationId: String): Corporation? {
        val partitionKey = mapOf(Corporation.DYNAMO_DB_PRIMARY_KEY to AttributeValue(corporationId))
        val dynamoDbResult = dynamoDb.getItem(
            GetItemRequest(CORPORATIONS_TABLE, partitionKey)
        )

        return dynamoDbResult?.let { Corporation.fromDynamoDbResult(it.item) }
    }

    fun save(corporation: Corporation): String {
        val itemValues = corporation.toMap()
        val result = dynamoDb.putItem(CORPORATIONS_TABLE, itemValues)

        val corporationSearchableObject = CorporationSearchableObject.fromCorporation(corporation)
        val jsonObject = getAsJsonObject(corporationSearchableObject)

        runBlocking {
            searchClient.saveObject(CORPORATIONS_INDEX, jsonObject)
        }

        return corporation.corporateId
    }

    private fun getAsJsonObject(corporationSearchableObject: CorporationSearchableObject): JsonObject {
        val jsonElement = Json.encodeToJsonElement(corporationSearchableObject)
        return jsonElement as JsonObject
    }
}
