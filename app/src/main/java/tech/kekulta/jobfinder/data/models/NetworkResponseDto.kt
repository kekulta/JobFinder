package tech.kekulta.jobfinder.data.models

import kotlinx.serialization.Serializable

@Serializable
data class NetworkResponseDto(
    val offers: List<OfferDto>,
    val vacancies: List<VacancyDto>,
)