package pl.hackyeah.backend.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import pl.hackyeah.backend.service.CharitiesService
import pl.hackyeah.backend.service.CorporationService

@RestController
@RequestMapping("/profile")
class ProfileController(
    private val charitiesService: CharitiesService,
    private val corporationService: CorporationService
) {

    @GetMapping
    fun getProfile(
        @RequestParam profileType: ProfileType,
        @RequestParam("user_id") userId: String
    ): ResponseEntity<Any> {
        if (profileType == ProfileType.CHARITY) {
            val charity = charitiesService.getCharity(userId)
            return charity?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()
        } else if (profileType == ProfileType.CORPORATE) {
            val corporation = corporationService.getCorporation(userId)
            return corporation?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()
        }

        return ResponseEntity.notFound().build()
    }
}