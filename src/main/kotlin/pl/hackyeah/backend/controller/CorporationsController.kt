package pl.hackyeah.backend.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.hackyeah.backend.entity.Corporate
import pl.hackyeah.backend.service.CorporateService
import java.util.UUID

@RestController
@RequestMapping("/corporations")
class CorporationsController(private val corporateService: CorporateService) {

    @PostMapping
    fun addCorporation(@RequestBody corporate: Corporate): ResponseEntity<CorporateResponseDto> {
        val corporateId = corporateService.createCorporate(corporate)
        val responseDto = CorporateResponseDto(corporateId)

        return ResponseEntity.ok(responseDto)
    }
}