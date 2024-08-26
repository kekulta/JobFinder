package tech.kekulta.navigation

import kotlinx.serialization.Serializable

@Serializable
enum class Destination {
    // Screens
    SEARCH, LIKED, APPLICATIONS, MESSAGES, PROFILE, ENTER_MAIL, ENTER_PIN, VACANCY_DETAILS,

    // Dialogs
    APPLICATION_DIALOG
}

object DestArgs {
    const val VACANCY_ID = "VACANCY_ID"
}