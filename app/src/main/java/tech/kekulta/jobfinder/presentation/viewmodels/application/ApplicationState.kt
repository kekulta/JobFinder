package tech.kekulta.jobfinder.presentation.viewmodels.application

sealed interface ApplicationState {
    data object Loading : ApplicationState
    data class NoCover(val title: String) : ApplicationState
    data class OpenCover(val title: String) : ApplicationState
}