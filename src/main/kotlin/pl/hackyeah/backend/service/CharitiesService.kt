package pl.hackyeah.backend.service

import org.springframework.stereotype.Service
import pl.hackyeah.backend.entity.Charity
import pl.hackyeah.backend.repository.CharitiesRepository

@Service
class CharitiesService(private val charitiesRepository: CharitiesRepository) {

    fun createCharity(charity: Charity): String {
        return charitiesRepository.save(charity)
    }

    fun getCharity(charityId: String): Charity? {
        return charitiesRepository.get(charityId)
    }
}