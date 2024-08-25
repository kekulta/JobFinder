package tech.kekulta.jobfinder.presentation.ui.recycler.adapters

import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import tech.kekulta.jobfinder.presentation.ui.customviews.SearchBlockView
import tech.kekulta.jobfinder.presentation.ui.events.EventDispatcher
import tech.kekulta.jobfinder.presentation.ui.events.UiEvent
import tech.kekulta.jobfinder.presentation.ui.events.UiEventDispatcher
import tech.kekulta.jobfinder.presentation.ui.recycler.base.BindableDelegateAdapter
import tech.kekulta.jobfinder.presentation.ui.recycler.base.BindableViewHolder
import tech.kekulta.jobfinder.presentation.ui.recycler.items.SearchBlockItem

class SearchBlockAdapter :
    BindableDelegateAdapter<SearchBlockItem, SearchBlockAdapter.SearchBlockVh>(
        setOf(
            SearchBlockItem.Search::class.java,
            SearchBlockItem.Idle::class.java
        )
    ), EventDispatcher<UiEvent> by UiEventDispatcher() {

    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        SearchBlockVh(SearchBlockView(parent.context).also {
            it.setLayoutParams(
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                )
            )
            it.setParent(this)
        })

    class SearchBlockVh(view: SearchBlockView) :
        BindableViewHolder<SearchBlockItem, SearchBlockView>(view)
}