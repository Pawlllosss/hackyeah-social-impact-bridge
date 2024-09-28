package pl.hackyeah.backend.configuration

import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider
import com.amazonaws.auth.InstanceProfileCredentialsProvider
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment

//private val logger = KotlinLogging.logger {}

@Configuration
private class DynamoDBConfig(private val environment: Environment) {
    @Value("\${amazon.dynamodb.endpoint}")
    private val amazonDynamoDBEndpoint: String? = null

    @Bean
    fun amazonDynamoDB(): AmazonDynamoDB {
        val amazonDynamoDB: AmazonDynamoDBClientBuilder = AmazonDynamoDBClientBuilder.standard()
        return amazonDynamoDB.withCredentials(credentialsProvider()).withEndpointConfiguration(
            AwsClientBuilder.EndpointConfiguration(amazonDynamoDBEndpoint, "us-east-1")).build()
    }

    fun credentialsProvider(): AWSCredentialsProvider {
        return if (environment.activeProfiles.any {
//                logger.info { "One of active profiles: $it" }
                it == "aws"
            }) InstanceProfileCredentialsProvider.getInstance() else EnvironmentVariableCredentialsProvider()
    }

}