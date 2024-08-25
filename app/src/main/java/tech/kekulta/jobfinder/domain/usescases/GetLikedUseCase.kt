package tech.kekulta.jobfinder.domain.usescases

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import tech.kekulta.jobfinder.domain.models.VacancyModel
import tech.kekulta.jobfinder.domain.repositories.LikesRepository
import tech.kekulta.jobfinder.domain.repositories.VacanciesRepository

@OptIn(ExperimentalCoroutinesApi::class)
class GetLikedUseCase(
    private val vacanciesRepository: VacanciesRepository,
    private val likesRepository: LikesRepository,
) {
    fun execute(): Flow<List<VacancyModel>> {
        return likesRepository.observeLikedVacancies()
            .flatMapLatest { vacanciesRepository.observeByIds(it) }
            .map { list -> list.map { it.copy(isFavorite = true) } }
    }
}