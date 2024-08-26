package tech.kekulta.jobfinder.data.datasources


import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import tech.kekulta.jobfinder.R
import tech.kekulta.jobfinder.data.models.NetworkResponseDto
import tech.kekulta.jobfinder.data.models.OfferDto
import tech.kekulta.jobfinder.data.models.VacancyDto
import java.io.IOException
import java.io.InputStream


class MockedRemoteDataSource(private val context: Context) : DataSource {
    override fun fetchRecommendations(): Flow<List<VacancyDto>> {
        return fetch().map { it?.vacancies ?: emptyList() }
    }

    override fun fetchOffers(): Flow<List<OfferDto>> {
        return fetch().map { it?.offers ?: emptyList() }
    }

    private fun fetch(): Flow<NetworkResponseDto?> {
        return flow {
            val res = inputStreamToString(
                context.resources.openRawResource(
                    R.raw.mock
                )
            )?.let {
                val json = Json {
                    ignoreUnknownKeys = true
                    explicitNulls = false
                }
                json.decodeFromString<NetworkResponseDto>(it)
            }

            emit(res)
        }
    }

    private fun inputStreamToString(inputStream: InputStream): String? {
        try {
            val bytes = ByteArray(inputStream.available())
            inputStream.read(bytes, 0, bytes.size)
            val json = String(bytes)
            return json
        } catch (e: IOException) {
            return null
        }
    }
}