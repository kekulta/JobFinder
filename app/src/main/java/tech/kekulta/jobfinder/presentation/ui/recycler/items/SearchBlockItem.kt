package tech.kekulta.jobfinder.presentation.ui.recycler.items

import tech.kekulta.jobfinder.presentation.ui.recycler.base.DelegateAdapterItem

sealed class SearchBlockItem(
    open val id: String, open val hint: String,
) : DelegateAdapterItem {
    override fun id() = id
    override fun content() = this

    data class Search(override val id: String, override val hint: String, val foundCount: Int) :
        SearchBlockItem(id, hint)

    data class Idle(override val id: String, override val hint: String) :
        SearchBlockItem(id, hint)
}