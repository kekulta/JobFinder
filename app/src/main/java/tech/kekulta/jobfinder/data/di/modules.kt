package tech.kekulta.jobfinder.data.di

import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.converter.FlowConverterFactory
import de.jensklingenberg.ktorfit.ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import tech.kekulta.jobfinder.data.datasources.DataSource
import tech.kekulta.jobfinder.data.datasources.FallbackDataSource
import tech.kekulta.jobfinder.data.datasources.FinderApi
import tech.kekulta.jobfinder.data.datasources.MockedRemoteDataSource
import tech.kekulta.jobfinder.data.datasources.RemoteDataSource
import tech.kekulta.jobfinder.data.datasources.createFinderApi
import tech.kekulta.jobfinder.data.mappers.OfferDtoToOfferModelMapper
import tech.kekulta.jobfinder.data.mappers.VacancyDtoToVacancyModelMapper
import tech.kekulta.jobfinder.data.repositories.LikesRepositoryImpl
import tech.kekulta.jobfinder.data.repositories.OffersRepositoryImpl
import tech.kekulta.jobfinder.data.repositories.VacanciesRepositoryImpl
import tech.kekulta.jobfinder.domain.repositories.LikesRepository
import tech.kekulta.jobfinder.domain.repositories.OffersRepository
import tech.kekulta.jobfinder.domain.repositories.VacanciesRepository

val dataModule = module {
    singleOf(::VacanciesRepositoryImpl) bind VacanciesRepository::class
    singleOf(::LikesRepositoryImpl) bind LikesRepository::class
    singleOf(::OffersRepositoryImpl) bind OffersRepository::class
    singleOf(::MockedRemoteDataSource)
    singleOf(::RemoteDataSource)
    singleOf(::FallbackDataSource) bind DataSource::class
    single {
        ktorfit {
            baseUrl(FinderApi.BASE_URL)
            httpClient(HttpClient {
                install(ContentNegotiation) {
                    json(Json { isLenient = true; ignoreUnknownKeys = true })
                    json(
                        Json { isLenient = true; ignoreUnknownKeys = true },
                        contentType = ContentType.Application.OctetStream,
                    )
                }
            })
            converterFactories(
                FlowConverterFactory(),
            )

        }
    }

    single { get<Ktorfit>().createFinderApi() }

    factoryOf(::VacancyDtoToVacancyModelMapper)
    factoryOf(::OfferDtoToOfferModelMapper)
}