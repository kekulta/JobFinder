package tech.kekulta.jobfinder.presentation.ui.recycler.adapters

import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import tech.kekulta.jobfinder.domain.models.OfferModel
import tech.kekulta.jobfinder.presentation.ui.customviews.OfferView
import tech.kekulta.jobfinder.presentation.ui.events.EventDispatcher
import tech.kekulta.jobfinder.presentation.ui.events.UiEvent
import tech.kekulta.jobfinder.presentation.ui.events.UiEventDispatcher
import tech.kekulta.jobfinder.presentation.ui.recycler.base.BindableViewHolder
import tech.kekulta.jobfinder.presentation.ui.recycler.base.DelegateAdapter
import tech.kekulta.jobfinder.presentation.ui.recycler.items.OfferItem
import tech.kekulta.uikit.dp

class OfferAdapter :
    DelegateAdapter<OfferItem, OfferAdapter.OfferVh>(OfferItem::class.java),
    EventDispatcher<UiEvent> by UiEventDispatcher() {

    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        OfferVh(OfferView(parent.context).also {
            it.setLayoutParams(
                LinearLayout.LayoutParams(
                    parent.context.dp(132).toInt(),
                    parent.context.dp(132).toInt(),
                )
            )
            it.setParent(this)
        })

    override fun bind(model: OfferItem, viewHolder: OfferVh) {
        viewHolder.bind(model.offer)
    }

    class OfferVh(view: OfferView) : BindableViewHolder<OfferModel, OfferView>(view)
}

