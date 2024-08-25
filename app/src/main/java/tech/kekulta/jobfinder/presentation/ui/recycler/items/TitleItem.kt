package tech.kekulta.jobfinder.presentation.ui.recycler.items

import tech.kekulta.jobfinder.presentation.ui.recycler.base.DelegateAdapterItem

data class TitleItem(val title: String, val subtitle: String? = null) : DelegateAdapterItem {
    override fun id() = title
    override fun content() = this
}