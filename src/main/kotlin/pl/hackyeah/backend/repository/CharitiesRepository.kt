package pl.hackyeah.backend.repository

import com.algolia.client.api.SearchClient
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.encodeToJsonElement
import org.springframework.stereotype.Repository
import pl.hackyeah.backend.entity.Charity
import java.util.UUID

@Repository
class CharitiesRepository(
    private val dynamoDb: AmazonDynamoDB,
    private val searchClient: SearchClient,
) {


    companion object {
        private const val CHARITIES_INDEX = "charities"
        private const val CHARITY_TABLE = "charities"
    }

    fun save(charity: Charity): String {
        val itemValues = charity.toMap()
        val result = dynamoDb.putItem(CHARITY_TABLE, itemValues)

        val jsonObject = getAsJsonObject(charity)

        runBlocking {
            searchClient.saveObject(CHARITIES_INDEX, jsonObject)
        }

        return charity.charityId
    }

    private fun getAsJsonObject(charity: Charity): JsonObject {
        val jsonElement = Json.encodeToJsonElement(charity)
        return jsonElement as JsonObject
    }
}
