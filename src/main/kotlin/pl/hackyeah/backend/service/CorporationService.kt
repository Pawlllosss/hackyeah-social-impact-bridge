package pl.hackyeah.backend.service

import org.springframework.stereotype.Service
import pl.hackyeah.backend.entity.Corporation
import pl.hackyeah.backend.repository.CorporationRepository
import java.util.UUID

@Service
class CorporationService(private val corporationRepository: CorporationRepository) {

    fun createCorporation(corporation: Corporation): String {
        return corporationRepository.save(corporation)
    }

    fun getCorporation(corporationId: String): Corporation? {
        return corporationRepository.get(corporationId)
    }
}