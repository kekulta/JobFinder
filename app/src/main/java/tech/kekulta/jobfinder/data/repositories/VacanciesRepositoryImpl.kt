package tech.kekulta.jobfinder.data.repositories

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import tech.kekulta.jobfinder.data.datasources.DataSource
import tech.kekulta.jobfinder.data.mappers.VacancyDtoToVacancyModelMapper
import tech.kekulta.jobfinder.domain.models.VacancyId
import tech.kekulta.jobfinder.domain.models.VacancyModel
import tech.kekulta.jobfinder.domain.repositories.VacanciesRepository

@OptIn(ExperimentalCoroutinesApi::class)
class VacanciesRepositoryImpl(
    private val dataSource: DataSource,
    private val vacancyDtoToVacancyModelMapper: VacancyDtoToVacancyModelMapper,
) : AbstractCoroutineManager(), VacanciesRepository {
    private val updater = MutableStateFlow(0)

    override fun observeRecommendation(): Flow<List<VacancyModel>> = updater.flatMapLatest {
        dataSource.fetchRecommendations().map { vacancyDtoToVacancyModelMapper.map(it) }
    }

    override fun observeByIds(ids: List<VacancyId>): Flow<List<VacancyModel>> {
        return updater.flatMapLatest {
            dataSource.fetchRecommendations().map {
                vacancyDtoToVacancyModelMapper.map(it).filter { vacancy -> vacancy.id in ids }
            }
        }
    }

    override fun fetch() {
        updater.value++
    }
}