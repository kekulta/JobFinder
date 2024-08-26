package tech.kekulta.jobfinder.data.datasources

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import logcat.LogPriority
import logcat.logcat
import tech.kekulta.jobfinder.data.models.NetworkResponseDto
import tech.kekulta.jobfinder.data.models.OfferDto
import tech.kekulta.jobfinder.data.models.VacancyDto
import tech.kekulta.jobfinder.data.repositories.AbstractCoroutineManager

class RemoteDataSource(private val api: FinderApi) : AbstractCoroutineManager(), DataSource {
    // Server takes 1-2 seconds te respond, so cache is a necessity
    private val cachedResponse = MutableStateFlow<NetworkResponseDto?>(null)

    override fun fetchRecommendations(): Flow<List<VacancyDto>> {
        fetch()
        return cachedResponse.map { it?.vacancies ?: emptyList() }
    }

    override fun fetchOffers(): Flow<List<OfferDto>> {
        fetch()
        return cachedResponse.map { it?.offers ?: emptyList() }
    }

    private fun fetch() {
        logcat { "Start fetching!" }
        launchScope(this, keepOld = true) {
            (api.getRecommendations(FinderApi.ID, FinderApi.EXPORT) as Flow<NetworkResponseDto?>)
                .catch { e ->
                    logcat(LogPriority.ERROR) { e.toString() }
                    emit(null)
                }.collect { newResponse -> cachedResponse.update { newResponse } }
        }
    }
}