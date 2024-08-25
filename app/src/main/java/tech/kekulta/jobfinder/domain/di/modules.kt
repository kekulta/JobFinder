package tech.kekulta.jobfinder.domain.di

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import tech.kekulta.jobfinder.domain.interactors.LoginInteractor
import tech.kekulta.jobfinder.domain.usescases.GetLikedUseCase
import tech.kekulta.jobfinder.domain.usescases.GetRecommendationsUseCase
import tech.kekulta.jobfinder.domain.usescases.ObserveByIdUseCase

val domainModule = module {
    singleOf(::LoginInteractor)

    factoryOf(::GetRecommendationsUseCase)
    factoryOf(::GetLikedUseCase)
    factoryOf(::ObserveByIdUseCase)
}