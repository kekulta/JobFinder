package tech.kekulta.jobfinder.data.models

import kotlinx.serialization.Serializable

@Serializable
data class VacancyDto(
    val id: String? = null,
    val lookingNumber: Int? = null,
    val title: String? = null,
    val address: Map<String, String>? = null,
    val company: String? = null,
    val experience: Map<String, String>? = null,
    val publishedDate: String? = null,
    val isFavorite: Boolean? = null,
    val salary: Map<String, String>? = null,
    val schedules: List<String>? = null,
    val appliedNumber: Int? = null,
    val description: String? = null,
    val responsibilities: String? = null,
    val questions: List<String>? = null,
)
