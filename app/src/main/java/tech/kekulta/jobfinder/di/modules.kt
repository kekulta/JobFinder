package tech.kekulta.jobfinder.di

import org.koin.dsl.module
import tech.kekulta.jobfinder.data.di.dataModule
import tech.kekulta.jobfinder.domain.di.domainModule
import tech.kekulta.jobfinder.presentation.di.presentationModule

val appModules = module { includes(dataModule, domainModule, presentationModule) }
