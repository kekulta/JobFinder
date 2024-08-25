package tech.kekulta.jobfinder.domain.repositories

import kotlinx.coroutines.flow.Flow
import tech.kekulta.jobfinder.domain.models.OfferModel

interface OffersRepository {
    fun observeRecommendation(): Flow<List<OfferModel>>
    fun fetch()
}