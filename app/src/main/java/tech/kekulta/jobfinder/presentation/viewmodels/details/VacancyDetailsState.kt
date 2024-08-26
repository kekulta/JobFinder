package tech.kekulta.jobfinder.presentation.viewmodels.details

import tech.kekulta.jobfinder.domain.models.VacancyModel

sealed interface VacancyDetailsState {
    data object Loading : VacancyDetailsState
    data class Loaded(val model: VacancyModel) : VacancyDetailsState
}