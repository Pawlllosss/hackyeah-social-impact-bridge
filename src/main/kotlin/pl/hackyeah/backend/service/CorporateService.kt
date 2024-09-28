package pl.hackyeah.backend.service

import org.springframework.stereotype.Service
import pl.hackyeah.backend.entity.Corporate
import pl.hackyeah.backend.repository.CorporateRepository
import java.util.UUID

@Service
class CorporateService(private val corporateRepository: CorporateRepository) {

    fun createCorporate(corporate: Corporate): UUID {
        return corporateRepository.save(corporate)
    }
}