package pl.hackyeah.backend.configuration

import aws.sdk.kotlin.services.comprehend.ComprehendClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client

@Configuration
class ComprehendEnglishClientConfiguration(private val environment: Environment) {

    @Value("\${amazon.s3.region}")
    private val reg: String? = null

    @Bean
    fun comprehendClient(): ComprehendClient {
        return ComprehendClient { region = reg }
    }
}