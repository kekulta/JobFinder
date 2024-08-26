package tech.kekulta.jobfinder.presentation.viewmodels.search

import tech.kekulta.jobfinder.domain.models.OfferModel
import tech.kekulta.jobfinder.domain.models.VacancyModel

sealed class SearchState(
    open val vacancies: List<VacancyModel>,
    open val vacanciesCount: Int,
) {
    data class Recommendations(
        val offers: List<OfferModel>,
        override val vacanciesCount: Int,
        override val vacancies: List<VacancyModel>
    ) : SearchState(vacancies, vacanciesCount)


    data class Search(
        val search: String?,
        override val vacanciesCount: Int,
        override val vacancies: List<VacancyModel>
    ) : SearchState(vacancies, vacanciesCount)
}

