package tech.kekulta.jobfinder.domain.repositories

import kotlinx.coroutines.flow.Flow
import tech.kekulta.jobfinder.domain.models.VacancyId
import tech.kekulta.jobfinder.domain.models.VacancyModel

interface VacanciesRepository {
    fun observeRecommendation(): Flow<List<VacancyModel>>
    fun observeByIds(ids: List<VacancyId>): Flow<List<VacancyModel>>
    fun fetch()
}