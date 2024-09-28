package pl.hackyeah.backend.configuration

import com.algolia.client.api.SearchClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SearchClientConfiguration {

    @Bean
    fun searchClient(): SearchClient {
        val applicationID = System.getenv("ALGOLIA_APPLICATION_ID")
            ?: throw IllegalArgumentException("ALGOLIA_APPLICATION_ID is not set")
        val apiKey = System.getenv("ALGOLIA_ADMIN_API_KEY")
            ?: throw IllegalArgumentException("ALGOLIA_ADMIN_API_KEY is not set")

        return SearchClient(applicationID, apiKey)
    }
}