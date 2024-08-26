package tech.kekulta.jobfinder.presentation.viewmodels.application

sealed interface ApplicationEvent {
    data object CloseDialog : ApplicationEvent
}