package tech.kekulta.jobfinder.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import tech.kekulta.jobfinder.domain.models.VacancyId
import tech.kekulta.jobfinder.domain.repositories.LikesRepository

class LikesRepositoryImpl : LikesRepository {
    private val likes = MutableStateFlow<Set<VacancyId>>(emptySet())

    override fun observeLikedVacancies(): Flow<List<VacancyId>> = likes.map { it.toList() }

    override fun likeVacancy(id: VacancyId) {
        likes.value += id
    }

    override fun dislikeVacancy(id: VacancyId) {
        likes.value -= id
    }
}