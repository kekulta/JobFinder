package tech.kekulta.jobfinder.presentation.viewmodels

import androidx.lifecycle.ViewModel
import tech.kekulta.jobfinder.presentation.ui.events.BackHandle
import tech.kekulta.jobfinder.presentation.ui.events.EventDispatcher
import tech.kekulta.jobfinder.presentation.ui.events.NavEventDispatcher
import tech.kekulta.jobfinder.presentation.ui.events.UiEvent
import tech.kekulta.jobfinder.presentation.ui.events.UiEventDispatcher

class ProfileViewModel(
    private val navEventDispatcher: NavEventDispatcher,
) : ViewModel(), EventDispatcher<UiEvent> by UiEventDispatcher() {

    init {
        setHandle(BackHandle(navEventDispatcher))
    }
}