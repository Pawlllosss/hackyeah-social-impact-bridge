package pl.hackyeah.backend.entity

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable

@DynamoDBTable(tableName = "corporations")
data class Corporate(

    @DynamoDBHashKey(attributeName = "corporate_id") val corporateId: Long,
    val name: String
)
