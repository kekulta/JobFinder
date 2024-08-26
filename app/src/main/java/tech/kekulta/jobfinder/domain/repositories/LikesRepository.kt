package tech.kekulta.jobfinder.domain.repositories

import kotlinx.coroutines.flow.Flow
import tech.kekulta.jobfinder.domain.models.VacancyId

interface LikesRepository {
    fun observeLikedVacancies(): Flow<List<VacancyId>>
    fun likeVacancy(id: VacancyId)
    fun dislikeVacancy(id: VacancyId)
}