package tech.kekulta.jobfinder.domain.usescases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import tech.kekulta.jobfinder.domain.models.VacancyModel
import tech.kekulta.jobfinder.domain.repositories.LikesRepository
import tech.kekulta.jobfinder.domain.repositories.VacanciesRepository

class GetRecommendationsUseCase(
    private val vacanciesRepository: VacanciesRepository,
    private val likesRepository: LikesRepository,
) {
    fun execute(): Flow<List<VacancyModel>> {
        return combine(
            vacanciesRepository.observeRecommendation(),
            likesRepository.observeLikedVacancies()
        ) { recs, likes ->
            recs.map { it.copy(isFavorite = likes.contains(it.id)) }
        }
    }
}