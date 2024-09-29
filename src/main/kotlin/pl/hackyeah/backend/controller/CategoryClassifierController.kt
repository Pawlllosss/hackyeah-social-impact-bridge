package pl.hackyeah.backend.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.hackyeah.backend.controller.dto.CategoryClassifierDto
import pl.hackyeah.backend.controller.dto.CategoryResponseDto
import pl.hackyeah.backend.service.CategoryClassifierService

@RestController
@RequestMapping("/categories-classifier")
class CategoryClassifierController(private val categoryClassifierService: CategoryClassifierService) {

    @PostMapping
    fun classifyCategory(@RequestBody categoryClassifierDto: CategoryClassifierDto): ResponseEntity<CategoryResponseDto> {
        val categories = categoryClassifierService.classifyCategory(categoryClassifierDto.sentence)
        val response = CategoryResponseDto(categories)

        return ResponseEntity.ok(response)
    }
}