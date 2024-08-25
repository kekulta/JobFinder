package tech.kekulta.navigation

import kotlinx.serialization.Serializable

@Serializable
enum class Destination {
    // Screens
    SEARCH, LIKED, RESPONSES, MESSAGES, PROFILE, ENTER_MAIL, ENTER_PIN, VACANCY_DETAILS,

    // Dialogs
    VACANCY_RESPONSE
}

object DestArgs {
    const val VACANCY_ID = "VACANCY_ID"
}