package tech.kekulta.jobfinder.data.datasources

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import logcat.logcat
import tech.kekulta.jobfinder.data.models.OfferDto
import tech.kekulta.jobfinder.data.models.VacancyDto

@OptIn(ExperimentalCoroutinesApi::class)
class FallbackDataSource(
    private val remoteDataSource: RemoteDataSource,
    private val mockedRemoteDataSource: MockedRemoteDataSource,
) : DataSource {
    override fun fetchRecommendations(): Flow<List<VacancyDto>> {
        return remoteDataSource.fetchRecommendations().flatMapLatest {
            if (it.isEmpty()) {
                logcat { "Get empty recommendations, fallback to local." }
                mockedRemoteDataSource.fetchRecommendations()
            } else {
                flow { emit(it) }
            }
        }
    }

    override fun fetchOffers(): Flow<List<OfferDto>> {
        return remoteDataSource.fetchOffers().flatMapLatest {
            if (it.isEmpty()) {
                logcat { "Get empty offers, fallback to local." }
                mockedRemoteDataSource.fetchOffers()
            } else {
                flow { emit(it) }
            }
        }
    }

}