package pl.hackyeah.backend.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.io.InputStream
import java.net.URL

@Service
class S3Service(
    private val s3Client: S3Client, @Value("\${amazon.s3.bucket-name}")
    private val bucketName: String
) {

    fun uploadFile(fileName: String, inputStream: InputStream, contentType: String): URL {
        val putObjectRequest = PutObjectRequest.builder()
            .bucket(bucketName)
            .key(fileName)
            .contentType(contentType)
            .build()

        // Upload the file
        s3Client.putObject(
            putObjectRequest,
            software.amazon.awssdk.core.sync.RequestBody.fromInputStream(inputStream, inputStream.available().toLong())
        )

        // Return the URL of the uploaded file
        return s3Client.utilities().getUrl { builder ->
            builder.bucket(bucketName).key(fileName)
        }
    }
}
