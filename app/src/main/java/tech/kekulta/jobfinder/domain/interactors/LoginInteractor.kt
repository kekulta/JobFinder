package tech.kekulta.jobfinder.domain.interactors

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

enum class LoginStatus {
    UNAUTHORIZED, PIN_SENT, AUTHORIZED
}

class LoginInteractor {
    private val status = MutableStateFlow(LoginStatus.UNAUTHORIZED)

    fun observeStatus(): StateFlow<LoginStatus> = status

    fun sendPin(email: String) {
        status.value = LoginStatus.PIN_SENT
    }

    fun enterPin(pin: String) {
        status.value = LoginStatus.AUTHORIZED
    }
}