package tech.kekulta.jobfinder.presentation.ui.recycler.items

import tech.kekulta.jobfinder.domain.models.OfferModel
import tech.kekulta.jobfinder.presentation.ui.recycler.base.DelegateAdapterItem

data class OfferBlockItem(val id: String, val offers: List<OfferModel>) : DelegateAdapterItem {
    override fun id() = id
    override fun content() = offers
}