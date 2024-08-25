package tech.kekulta.jobfinder.presentation.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import tech.kekulta.jobfinder.domain.interactors.LoginInteractor
import tech.kekulta.jobfinder.domain.interactors.LoginStatus
import tech.kekulta.jobfinder.presentation.navigation.DestBottomNavigator
import tech.kekulta.jobfinder.presentation.ui.events.BackHandle
import tech.kekulta.jobfinder.presentation.ui.events.EventDispatcher
import tech.kekulta.jobfinder.presentation.ui.events.NavEventDispatcher
import tech.kekulta.jobfinder.presentation.ui.events.UiEvent
import tech.kekulta.jobfinder.presentation.ui.events.UiEventDispatcher
import tech.kekulta.navigation.BottomNavigator
import tech.kekulta.navigation.DestNavigator
import tech.kekulta.navigation.Destination
import tech.kekulta.navigation.Navigator

class MainViewModel(
    private val navigator: DestNavigator,
    private val bottomNavigator: DestBottomNavigator,
    private val navDispatcher: NavEventDispatcher,
    private val likedViewModel: LikedViewModel,
    private val loginInteractor: LoginInteractor,
) : ViewModel(), EventDispatcher<UiEvent> by UiEventDispatcher() {

    init {
        setHandle(BackHandle(navDispatcher))
    }

    fun getStartDestination(): Destination {
        return when (loginInteractor.observeStatus().value) {
            LoginStatus.UNAUTHORIZED -> Destination.ENTER_MAIL
            LoginStatus.PIN_SENT -> Destination.ENTER_PIN
            LoginStatus.AUTHORIZED -> Destination.SEARCH
        }
    }

    fun getNavigator(): Navigator<Destination> = navigator
    fun getBottomNavigator(): BottomNavigator<Destination> = bottomNavigator
    fun observeBadgeStatus(): Flow<Int> = likedViewModel.observeLikes().map { it.size }
}