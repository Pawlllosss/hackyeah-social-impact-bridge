package pl.hackyeah.backend.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.hackyeah.backend.entity.Charity
import pl.hackyeah.backend.service.CharitiesService

@RestController
@RequestMapping("/charities")
class CharitiesController(private val charitiesService: CharitiesService) {

    @PostMapping
    fun addCorporation(@RequestBody charity: Charity): ResponseEntity<CharitiesResponseDto> {
        val corporateId = charitiesService.createCharity(charity)

        return corporateId?.let { ResponseEntity.ok(CharitiesResponseDto(it)) } ?: ResponseEntity.notFound().build()
    }

    @GetMapping("/{id}")
    fun getCorporation(@PathVariable id: String): ResponseEntity<Charity> {
        val charity = charitiesService.getCharity(id)

        return charity?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()
    }
}