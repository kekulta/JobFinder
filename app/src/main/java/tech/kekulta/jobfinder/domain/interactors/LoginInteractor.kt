package tech.kekulta.jobfinder.domain.interactors

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

sealed interface LoginStatus {
    data object Unauthorized : LoginStatus
    data class PinSent(val email: String) : LoginStatus
    data object Authorized : LoginStatus
}

class LoginInteractor {
    private val status = MutableStateFlow<LoginStatus>(LoginStatus.Unauthorized)
    private var email: String? = null
    private var pin: String? = null

    fun observeStatus(): StateFlow<LoginStatus> = status

    fun sendPin(email: String) {
        status.value = LoginStatus.PinSent(email)
        this.email = email
    }

    fun enterPin(pin: String) {
        status.value = LoginStatus.Authorized
        this.pin = pin
    }
}