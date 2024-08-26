package tech.kekulta.jobfinder.presentation.viewmodels

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import tech.kekulta.jobfinder.domain.interactors.LoginInteractor
import tech.kekulta.jobfinder.domain.interactors.LoginStatus
import tech.kekulta.jobfinder.presentation.ui.events.BackHandle
import tech.kekulta.jobfinder.presentation.ui.events.EventDispatcher
import tech.kekulta.jobfinder.presentation.ui.events.NavEventDispatcher
import tech.kekulta.jobfinder.presentation.ui.events.SetRoot
import tech.kekulta.jobfinder.presentation.ui.events.UiEvent
import tech.kekulta.jobfinder.presentation.ui.events.UiEventDispatcher
import tech.kekulta.jobfinder.presentation.viewmodels.base.AbstractCoroutineViewModel
import tech.kekulta.navigation.Destination

class EnterPinViewModel(
    private val navEventDispatcher: NavEventDispatcher,
    private val loginInteractor: LoginInteractor,
) : AbstractCoroutineViewModel(), EventDispatcher<UiEvent> by UiEventDispatcher() {
    private var pin: String? = null
    private val email = MutableStateFlow<String?>(null)

    init {
        setHandle(BackHandle(navEventDispatcher))
        launchScope {
            loginInteractor.observeStatus().collect { status ->
                if (status is LoginStatus.PinSent) {
                    email.update { status.email }
                } else {
                    email.update { null }
                }
            }
        }
    }

    fun observeStatus(): Flow<String?> = email

    fun enterPin(pin: String) {
        this.pin = pin
        loginInteractor.enterPin(pin)
        navEventDispatcher.dispatch(SetRoot(Destination.SEARCH))
    }
}