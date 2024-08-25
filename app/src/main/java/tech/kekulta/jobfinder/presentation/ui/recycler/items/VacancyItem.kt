package tech.kekulta.jobfinder.presentation.ui.recycler.items

import tech.kekulta.jobfinder.domain.models.VacancyModel
import tech.kekulta.jobfinder.presentation.ui.recycler.base.DelegateAdapterItem

data class VacancyItem(
    val model: VacancyModel,
) : DelegateAdapterItem {
    override fun id() = model.id
    override fun content() = model
}
