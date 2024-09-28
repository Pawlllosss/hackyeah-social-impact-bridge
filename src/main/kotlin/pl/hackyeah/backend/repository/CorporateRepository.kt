package pl.hackyeah.backend.repository

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import org.springframework.stereotype.Repository
import pl.hackyeah.backend.entity.Corporate
import java.util.UUID

@Repository
class CorporateRepository(private val dynamoDb: AmazonDynamoDB) {

    fun save(corporate: Corporate): UUID {
        val id = UUID.randomUUID()
        val itemValues = mutableMapOf("corporate_id" to AttributeValue(id.toString()))
//            , "name" to corporate.name)

        val result = dynamoDb.putItem("corporations", itemValues)

        return id
    }
}
