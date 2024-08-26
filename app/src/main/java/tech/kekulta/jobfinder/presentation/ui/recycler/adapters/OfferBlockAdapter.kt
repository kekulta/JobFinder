package tech.kekulta.jobfinder.presentation.ui.recycler.adapters

import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import tech.kekulta.jobfinder.presentation.ui.events.EventDispatcher
import tech.kekulta.jobfinder.presentation.ui.events.UiEvent
import tech.kekulta.jobfinder.presentation.ui.events.UiEventDispatcher
import tech.kekulta.jobfinder.presentation.ui.recycler.base.CompositeAdapter
import tech.kekulta.jobfinder.presentation.ui.recycler.base.DelegateAdapter
import tech.kekulta.jobfinder.presentation.ui.recycler.decorations.RecyclerViewRightMargin
import tech.kekulta.jobfinder.presentation.ui.recycler.items.OfferBlockItem
import tech.kekulta.jobfinder.presentation.ui.recycler.items.OfferItem
import tech.kekulta.uikit.dimen
import tech.kekulta.uikit.R as uikit

class OfferBlockAdapter :
    DelegateAdapter<OfferBlockItem, OfferBlockAdapter.OfferBlockVh>(OfferBlockItem::class.java),
    EventDispatcher<UiEvent> by UiEventDispatcher() {

    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        OfferBlockVh(RecyclerView(parent.context).also {
            it.setLayoutParams(
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                )
            )
        }).also { it.setParent(this) }

    override fun bind(model: OfferBlockItem, viewHolder: OfferBlockVh) {
        viewHolder.bind(model)
    }

    class OfferBlockVh(view: RecyclerView) : RecyclerView.ViewHolder(view),
        EventDispatcher<UiEvent> by UiEventDispatcher() {
        private val offersAdapter =
            CompositeAdapter.Builder().add(OfferAdapter().also { it.setParent(this) }).build()

        private val offersLayoutManager =
            LinearLayoutManager(view.context, RecyclerView.HORIZONTAL, false)

        init {
            view.apply {
                this.adapter = offersAdapter
                this.layoutManager = offersLayoutManager
                this.itemAnimator?.changeDuration = 0
                this.addItemDecoration(RecyclerViewRightMargin(dimen(uikit.dimen.size_x8)))
            }
        }

        fun bind(model: OfferBlockItem) {
            offersAdapter.submitList(model.offers.map { OfferItem(it) })
        }
    }
}
