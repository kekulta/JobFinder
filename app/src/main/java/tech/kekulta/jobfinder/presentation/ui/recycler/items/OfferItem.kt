package tech.kekulta.jobfinder.presentation.ui.recycler.items

import tech.kekulta.jobfinder.domain.models.OfferModel
import tech.kekulta.jobfinder.presentation.ui.recycler.base.DelegateAdapterItem

data class OfferItem(val offer: OfferModel) : DelegateAdapterItem {
    override fun id() = offer
    override fun content() = offer
}