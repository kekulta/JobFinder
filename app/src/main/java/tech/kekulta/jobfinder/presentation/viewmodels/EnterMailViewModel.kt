package tech.kekulta.jobfinder.presentation.viewmodels

import androidx.lifecycle.ViewModel
import tech.kekulta.jobfinder.domain.interactors.LoginInteractor
import tech.kekulta.jobfinder.presentation.navigation.SlideInOut
import tech.kekulta.jobfinder.presentation.ui.events.BackHandle
import tech.kekulta.jobfinder.presentation.ui.events.EventDispatcher
import tech.kekulta.jobfinder.presentation.ui.events.NavEventDispatcher
import tech.kekulta.jobfinder.presentation.ui.events.NavigateTo
import tech.kekulta.jobfinder.presentation.ui.events.UiEvent
import tech.kekulta.jobfinder.presentation.ui.events.UiEventDispatcher
import tech.kekulta.navigation.Destination

class EnterMailViewModel(
    private val navEventDispatcher: NavEventDispatcher,
    private val loginInteractor: LoginInteractor,
) : ViewModel(), EventDispatcher<UiEvent> by UiEventDispatcher() {
    private var email: String? = null

    init {
        setHandle(BackHandle(navEventDispatcher))
    }

    fun enterEmail(email: String) {
        this.email = email
        loginInteractor.sendPin(email)
        navEventDispatcher.dispatch(NavigateTo(Destination.ENTER_PIN, animations = SlideInOut))
    }
}