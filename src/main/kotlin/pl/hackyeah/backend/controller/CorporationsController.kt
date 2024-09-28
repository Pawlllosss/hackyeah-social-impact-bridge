package pl.hackyeah.backend.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.hackyeah.backend.entity.Corporation
import pl.hackyeah.backend.service.CorporationService

@RestController
@RequestMapping("/corporations")
class CorporationsController(private val corporationService: CorporationService) {

    @PostMapping
    fun addCorporation(@RequestBody corporation: Corporation): ResponseEntity<CorporationResponseDto> {
        val corporateId = corporationService.createCorporation(corporation)

        return corporateId?.let { ResponseEntity.ok(CorporationResponseDto(it)) } ?: ResponseEntity.notFound().build()
    }

    @GetMapping("/{id}")
    fun getCorporation(@PathVariable id: String): ResponseEntity<Corporation> {
        val corporation = corporationService.getCorporation(id)

        return corporation?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()
    }
}