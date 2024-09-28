package pl.hackyeah.backend.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client

@Configuration
class S3ClientConfiguration(private val environment: Environment) {

    @Value("\${amazon.s3.region}")
    private val region: String? = null

    @Bean
    fun s3Client(): S3Client {
        return S3Client.builder()
            .region(Region.of(region))
            .credentialsProvider(credentialsProvider())
            .build()
    }

    fun credentialsProvider(): AwsCredentialsProvider {
        return if (environment.activeProfiles.any {
                it == "aws"
            }) software.amazon.awssdk.auth.credentials.InstanceProfileCredentialsProvider.create() else software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider.create()
    }
}