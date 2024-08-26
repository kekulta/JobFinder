package tech.kekulta.jobfinder.presentation.navigation

import logcat.logcat
import tech.kekulta.jobfinder.domain.interactors.LoginInteractor
import tech.kekulta.jobfinder.domain.interactors.LoginStatus
import tech.kekulta.jobfinder.presentation.ui.events.NavController
import tech.kekulta.jobfinder.presentation.ui.events.NavigateToRoot
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
            Destination.SEARCH, Destination.VACANCY_DETAILS, Destination.APPLICATION_DIALOG -> Tab.SEARCH
            Destination.LIKED, Destination.ENTER_MAIL, Destination.ENTER_PIN -> Tab.LIKED
            Destination.APPLICATIONS -> Tab.APPLICATIONS
            Destination.MESSAGES -> Tab.MESSAGES
            Destination.PROFILE -> Tab.PROFILE
        }.ordinal
    }

    override fun setOnNavigatedListener(listener: (dest: Destination?, root: Destination?) -> Unit) {
        navigator.setOnNavigatedListener(listener)
    }

    override fun openTab(destination: Destination) {
        if (loginInteractor.observeStatus().value == LoginStatus.Authorized) {
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
            Tab.APPLICATIONS -> Destination.APPLICATIONS
            Tab.MESSAGES -> Destination.MESSAGES
            Tab.PROFILE -> Destination.PROFILE
        }
    }
}