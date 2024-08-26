package tech.kekulta.jobfinder.data.datasources

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import tech.kekulta.jobfinder.data.models.NetworkResponseDto
import tech.kekulta.jobfinder.data.models.OfferDto
import tech.kekulta.jobfinder.data.models.VacancyDto

interface DataSource {
    fun fetchRecommendations(): Flow<List<VacancyDto>>
    fun fetchOffers(): Flow<List<OfferDto>>
}