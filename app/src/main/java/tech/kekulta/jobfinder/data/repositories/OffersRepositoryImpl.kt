package tech.kekulta.jobfinder.data.repositories

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import tech.kekulta.jobfinder.data.datasources.DataSource
import tech.kekulta.jobfinder.data.mappers.OfferDtoToOfferModelMapper
import tech.kekulta.jobfinder.domain.models.OfferModel
import tech.kekulta.jobfinder.domain.repositories.OffersRepository

@OptIn(ExperimentalCoroutinesApi::class)
class OffersRepositoryImpl(
    private val dataSource: DataSource,
    private val offersDtoToOfferModelMapper: OfferDtoToOfferModelMapper,
) : AbstractCoroutineManager(), OffersRepository {
    private val updater = MutableStateFlow(0)

    override fun observeRecommendation(): Flow<List<OfferModel>> = updater.flatMapLatest {
        dataSource.fetchOffers().map { offersDtoToOfferModelMapper.map(it) }
    }

    override fun fetch() {
        updater.value++
    }
}