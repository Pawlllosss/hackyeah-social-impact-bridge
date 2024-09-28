package pl.hackyeah.backend.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.hackyeah.backend.entity.Corporation
import pl.hackyeah.backend.service.CharitiesService

@RestController
@RequestMapping("/corporations")
class CorporationsController(private val charitiesService: CharitiesService) {

    @PostMapping
    fun addCorporation(@RequestBody corporation: Corporation): ResponseEntity<CharitiesResponseDto> {
//        val corporateId = charitiesService.createCharity(corporation)

        return ResponseEntity.notFound().build()
//        return corporateId?.let { ResponseEntity.ok(CharitiesResponseDto(it)) } ?: ResponseEntity.notFound().build()
    }
}