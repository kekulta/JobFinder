package tech.kekulta.jobfinder.domain.models

import kotlinx.datetime.LocalDate

data class VacancyModel(
    val id: VacancyId,
    val lookingNumber: Int,
    val title: String,
    val address: Address,
    val companyName: String,
    val experience: Experience,
    val publishedDate: LocalDate,
    val isFavorite: Boolean,
    val schedules: List<Schedule>,
    val salary: Salary,
    val appliedNumber: Int,
    val description: String?,
    val responsibilities: String?,
    val questions: List<String>,
)

