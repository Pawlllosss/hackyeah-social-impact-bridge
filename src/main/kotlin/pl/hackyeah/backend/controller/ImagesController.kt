package pl.hackyeah.backend.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import pl.hackyeah.backend.entity.Charity
import pl.hackyeah.backend.entity.FileUploadResponseDto
import pl.hackyeah.backend.service.CharitiesService
import pl.hackyeah.backend.service.S3Service
import java.net.URL

@RestController
@RequestMapping("/image")
class ImagesController(private val s3Service: S3Service) {

    @PostMapping("/upload")
    fun uploadFile(
        @RequestParam("file") file: MultipartFile
    ): ResponseEntity<FileUploadResponseDto> {
        val fileName = file.originalFilename ?: "default-file-name"
        val inputStream = file.inputStream
        val contentType = file.contentType ?: "application/octet-stream"

        // Upload the file to S3
        val fileUrl: URL = s3Service.uploadFile(fileName, inputStream, contentType)

        // Return the file URL in the response
        val result = FileUploadResponseDto(fileUrl)
        return ResponseEntity.ok(result)
    }
}