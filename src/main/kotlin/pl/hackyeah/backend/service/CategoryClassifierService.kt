package pl.hackyeah.backend.service

import aws.sdk.kotlin.services.comprehend.ComprehendClient
import aws.sdk.kotlin.services.comprehend.model.ClassifyDocumentRequest
import aws.sdk.kotlin.services.comprehend.model.ClassifyDocumentResponse
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class CategoryClassifierService(
    private val comprehendClient: ComprehendClient, @Value("\${amazon.comprehend.endpoint}")
    private val comprehendEndpoint: String
) {

    fun classifyCategory(sentence: String): List<String> {
        val request = ClassifyDocumentRequest {
            this.text = sentence
            this.endpointArn = comprehendEndpoint
        }

        var response: ClassifyDocumentResponse?

        runBlocking {
            response = comprehendClient.classifyDocument(request)
        }

        return response?.classes?.mapNotNull { it.name }?.toList() ?: emptyList()
    }
}