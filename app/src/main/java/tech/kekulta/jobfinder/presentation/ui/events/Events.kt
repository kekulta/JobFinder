package tech.kekulta.jobfinder.presentation.ui.events

import android.os.Bundle
import tech.kekulta.jobfinder.domain.models.VacancyId
import tech.kekulta.navigation.Animations
import tech.kekulta.navigation.Destination

sealed interface Event
sealed interface UiEvent : Event
sealed interface NavEvent : Event

data object BackPressed : UiEvent
data object ShowMore : UiEvent
data class LinkPressed(val link: String) : UiEvent
data object AddCover : UiEvent
data class VacancyPressed(val id: VacancyId) : UiEvent
data class ApplyPressed(val id: VacancyId) : UiEvent
data class LikeVacancyPressed(val id: VacancyId) : UiEvent
data class DislikeVacancyPressed(val id: VacancyId) : UiEvent

data class OpenLink(val link: String) : NavEvent
data class OpenDialog(val destination: Destination, val args: Bundle? = null) : NavEvent
data class SetRoot(val destination: Destination, val args: Bundle? = null) : NavEvent
data class NavigateToRoot(val destination: Destination, val args: Bundle? = null) : NavEvent
data class NavigateTo(
    val destination: Destination,
    val args: Bundle? = null,
    val animations: Animations? = null,
) : NavEvent

data object NavigateBack : NavEvent
