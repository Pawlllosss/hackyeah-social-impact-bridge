package pl.hackyeah.backend.service

import org.springframework.stereotype.Service
import pl.hackyeah.backend.entity.Corporation
import pl.hackyeah.backend.repository.CorporationRepository
import java.util.UUID

@Service
class CorporationService(private val corporationRepository: CorporationRepository) {

    fun createCorporation(corporation: Corporation): UUID? {
        val id = UUID.randomUUID()

        return corporationRepository.save(corporation.copy(corporateId = id))
    }
}