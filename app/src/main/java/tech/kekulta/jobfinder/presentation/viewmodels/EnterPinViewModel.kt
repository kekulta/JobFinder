package tech.kekulta.jobfinder.presentation.viewmodels

import androidx.lifecycle.ViewModel
import tech.kekulta.jobfinder.domain.interactors.LoginInteractor
import tech.kekulta.jobfinder.presentation.ui.events.BackHandle
import tech.kekulta.jobfinder.presentation.ui.events.EventDispatcher
import tech.kekulta.jobfinder.presentation.ui.events.NavEventDispatcher
import tech.kekulta.jobfinder.presentation.ui.events.SetRoot
import tech.kekulta.jobfinder.presentation.ui.events.UiEvent
import tech.kekulta.jobfinder.presentation.ui.events.UiEventDispatcher
import tech.kekulta.navigation.Destination

class EnterPinViewModel(
    private val navEventDispatcher: NavEventDispatcher,
    private val loginInteractor: LoginInteractor,
) : ViewModel(), EventDispatcher<UiEvent> by UiEventDispatcher() {
    private var pin: String? = null

    init {
        setHandle(BackHandle(navEventDispatcher))
    }

    fun enterPin(pin: String) {
        this.pin = pin
        loginInteractor.enterPin(pin)
        navEventDispatcher.dispatch(SetRoot(Destination.SEARCH))
    }
}