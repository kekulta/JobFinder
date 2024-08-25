package tech.kekulta.jobfinder.data.di

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import tech.kekulta.jobfinder.data.datasources.MockedRemoteDataSource
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
    factoryOf(::VacancyDtoToVacancyModelMapper)
    factoryOf(::OfferDtoToOfferModelMapper)
}