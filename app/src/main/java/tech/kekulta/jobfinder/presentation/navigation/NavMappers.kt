package tech.kekulta.jobfinder.presentation.navigation

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import tech.kekulta.jobfinder.presentation.ui.fragments.ApplicationBottomSheet
import tech.kekulta.jobfinder.presentation.ui.fragments.ApplicationsFragment
import tech.kekulta.jobfinder.presentation.ui.fragments.EnterMailFragment
import tech.kekulta.jobfinder.presentation.ui.fragments.EnterPinFragment
import tech.kekulta.jobfinder.presentation.ui.fragments.LikedFragment
import tech.kekulta.jobfinder.presentation.ui.fragments.MessagesFragment
import tech.kekulta.jobfinder.presentation.ui.fragments.ProfileFragment
import tech.kekulta.jobfinder.presentation.ui.fragments.SearchFragment
import tech.kekulta.jobfinder.presentation.ui.fragments.VacancyDetailsFragment
import tech.kekulta.navigation.Destination

fun getDialog(destination: Destination): DialogFragment {
    return when (destination) {
        Destination.SEARCH,
        Destination.LIKED,
        Destination.APPLICATIONS,
        Destination.MESSAGES,
        Destination.PROFILE,
        Destination.ENTER_MAIL,
        Destination.ENTER_PIN,
        Destination.VACANCY_DETAILS -> throw IllegalArgumentException("Destination must be dialog!")

        Destination.APPLICATION_DIALOG -> ApplicationBottomSheet()
    }
}

fun getFragment(destination: Destination): Class<out Fragment> {
    return when (destination) {
        Destination.SEARCH -> SearchFragment::class.java
        Destination.LIKED -> LikedFragment::class.java
        Destination.APPLICATIONS -> ApplicationsFragment::class.java
        Destination.MESSAGES -> MessagesFragment::class.java
        Destination.PROFILE -> ProfileFragment::class.java
        Destination.ENTER_MAIL -> EnterMailFragment::class.java
        Destination.ENTER_PIN -> EnterPinFragment::class.java
        Destination.VACANCY_DETAILS -> VacancyDetailsFragment::class.java

        Destination.APPLICATION_DIALOG -> throw IllegalArgumentException("Destination must not be dialog!")
    }
}
