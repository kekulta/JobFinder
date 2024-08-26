package tech.kekulta.jobfinder.presentation.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import tech.kekulta.jobfinder.presentation.navigation.DestBottomNavigator
import tech.kekulta.jobfinder.presentation.ui.events.NavController
import tech.kekulta.jobfinder.presentation.ui.events.NavEventDispatcher
import tech.kekulta.jobfinder.presentation.viewmodels.EnterMailViewModel
import tech.kekulta.jobfinder.presentation.viewmodels.EnterPinViewModel
import tech.kekulta.jobfinder.presentation.viewmodels.LikedViewModel
import tech.kekulta.jobfinder.presentation.viewmodels.MainViewModel
import tech.kekulta.jobfinder.presentation.viewmodels.MessagesViewModel
import tech.kekulta.jobfinder.presentation.viewmodels.ProfileViewModel
import tech.kekulta.jobfinder.presentation.viewmodels.application.ApplicationViewModel
import tech.kekulta.jobfinder.presentation.viewmodels.ApplicationsViewModel
import tech.kekulta.jobfinder.presentation.viewmodels.search.SearchViewModel
import tech.kekulta.jobfinder.presentation.viewmodels.details.VacancyDetailsViewModel
import tech.kekulta.navigation.DestNavigator

val presentationModule = module {
    viewModelOf(::EnterMailViewModel)
    viewModelOf(::EnterPinViewModel)
    viewModelOf(::LikedViewModel)
    viewModelOf(::MainViewModel)
    viewModelOf(::MessagesViewModel)
    viewModelOf(::ProfileViewModel)
    viewModelOf(::ApplicationsViewModel)
    viewModelOf(::SearchViewModel)
    viewModelOf(::VacancyDetailsViewModel)
    viewModelOf(::ApplicationViewModel)

    singleOf(::DestNavigator)
    singleOf(::DestBottomNavigator)
    singleOf(::NavController) bind NavEventDispatcher::class
}
