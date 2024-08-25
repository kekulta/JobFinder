package tech.kekulta.jobfinder.presentation.navigation

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import logcat.logcat
import tech.kekulta.jobfinder.domain.interactors.LoginInteractor
import tech.kekulta.jobfinder.domain.interactors.LoginStatus
import tech.kekulta.jobfinder.presentation.ui.events.NavController
import tech.kekulta.jobfinder.presentation.ui.events.NavigateToRoot
import tech.kekulta.jobfinder.presentation.ui.fragments.EnterMailFragment
import tech.kekulta.jobfinder.presentation.ui.fragments.EnterPinFragment
import tech.kekulta.jobfinder.presentation.ui.fragments.LikedFragment
import tech.kekulta.jobfinder.presentation.ui.fragments.MessagesFragment
import tech.kekulta.jobfinder.presentation.ui.fragments.ApplicationBottomSheet
import tech.kekulta.jobfinder.presentation.ui.fragments.ProfileFragment
import tech.kekulta.jobfinder.presentation.ui.fragments.ApplicationsFragment
import tech.kekulta.jobfinder.presentation.ui.fragments.SearchFragment
import tech.kekulta.jobfinder.presentation.ui.fragments.VacancyDetailsFragment
import tech.kekulta.navigation.BottomNavigator
import tech.kekulta.navigation.DestNavigator
import tech.kekulta.navigation.Destination
import tech.kekulta.navigation.Tab

class DestBottomNavigator(
    private val navigator: DestNavigator,
    private val navController: NavController,
    private val loginInteractor: LoginInteractor,
) : BottomNavigator<Destination> {

    override fun destinationToItem(destination: Destination): Int {
        return when (destination) {
            Destination.SEARCH, Destination.VACANCY_DETAILS, Destination.VACANCY_RESPONSE -> Tab.SEARCH
            Destination.LIKED, Destination.ENTER_MAIL, Destination.ENTER_PIN -> Tab.LIKED
            Destination.RESPONSES -> Tab.RESPONSES
            Destination.MESSAGES -> Tab.MESSAGES
            Destination.PROFILE -> Tab.PROFILE
        }.ordinal
    }

    override fun setOnNavigatedListener(listener: (dest: Destination?, root: Destination?) -> Unit) {
        navigator.setOnNavigatedListener(listener)
    }

    override fun openTab(destination: Destination) {
        if (loginInteractor.observeStatus().value == LoginStatus.AUTHORIZED) {
            navController.dispatch(NavigateToRoot(destination))
        } else {
            logcat { "BottomNavigation disabled in non authorized state!" }
        }
    }

    override fun itemToDestination(item: Int): Destination {
        val tab = Tab.entries[item]
        return when (tab) {
            Tab.SEARCH -> Destination.SEARCH
            Tab.LIKED -> Destination.LIKED
            Tab.RESPONSES -> Destination.RESPONSES
            Tab.MESSAGES -> Destination.MESSAGES
            Tab.PROFILE -> Destination.PROFILE
        }
    }
}

fun getDialog(destination: Destination): DialogFragment {
    return when (destination) {
        Destination.SEARCH,
        Destination.LIKED,
        Destination.RESPONSES,
        Destination.MESSAGES,
        Destination.PROFILE,
        Destination.ENTER_MAIL,
        Destination.ENTER_PIN,
        Destination.VACANCY_DETAILS -> throw IllegalArgumentException("Destination must be dialog!")

        Destination.VACANCY_RESPONSE -> ApplicationBottomSheet()
    }
}

fun getFragment(destination: Destination): Class<out Fragment> {
    return when (destination) {
        Destination.SEARCH -> SearchFragment::class.java
        Destination.LIKED -> LikedFragment::class.java
        Destination.RESPONSES -> ApplicationsFragment::class.java
        Destination.MESSAGES -> MessagesFragment::class.java
        Destination.PROFILE -> ProfileFragment::class.java
        Destination.ENTER_MAIL -> EnterMailFragment::class.java
        Destination.ENTER_PIN -> EnterPinFragment::class.java
        Destination.VACANCY_DETAILS -> VacancyDetailsFragment::class.java
        Destination.VACANCY_RESPONSE -> throw IllegalArgumentException("Destination must not be dialog!")
    }
}
