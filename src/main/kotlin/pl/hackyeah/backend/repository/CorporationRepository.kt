package pl.hackyeah.backend.repository

import com.algolia.client.api.SearchClient
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.encodeToJsonElement
import org.springframework.stereotype.Repository
import pl.hackyeah.backend.entity.Corporation
import java.util.UUID

@Repository
class CorporationRepository(
    private val dynamoDb: AmazonDynamoDB,
    private val searchClient: SearchClient,
) {


    companion object {
        private const val CORPORATIONS_INDEX = "corporations"
        private const val CORPORATIONS_TABLE = "corporations"
    }

    fun save(corporation: Corporation): UUID? {
        val itemValues = corporation.toMap()
        val result = dynamoDb.putItem(CORPORATIONS_TABLE, itemValues)

        val jsonObject = getAsJsonObject(corporation)

        runBlocking {
            searchClient.saveObject(CORPORATIONS_INDEX, jsonObject)
        }

        return corporation.corporateId
    }

    private fun getAsJsonObject(corporation: Corporation): JsonObject {
        val jsonElement = Json.encodeToJsonElement(corporation)
        return jsonElement as JsonObject
    }
}
