package pl.hackyeah.backend.service

import org.springframework.stereotype.Service
import pl.hackyeah.backend.entity.Charity
import pl.hackyeah.backend.repository.CharitiesRepository
import java.util.UUID

@Service
class CharitiesService(private val charitiesRepository: CharitiesRepository) {

    fun createCharity(charity: Charity): UUID? {
        val id = UUID.randomUUID()

        return charitiesRepository.save(charity.copy(charityId = id))
    }
}