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
import tech.kekulta.uikit.dimen
import tech.kekulta.uikit.R as uikit

class OfferAdapter :
    DelegateAdapter<OfferItem, OfferAdapter.OfferVh>(OfferItem::class.java),
    EventDispatcher<UiEvent> by UiEventDispatcher() {

    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        OfferVh(OfferView(parent.context).also {
            it.setLayoutParams(
                LinearLayout.LayoutParams(
                    parent.context.dimen(uikit.dimen.size_x132),
                    parent.context.dimen(uikit.dimen.size_x132),
                )
            )
            it.setParent(this)
        })

    override fun bind(model: OfferItem, viewHolder: OfferVh) {
        viewHolder.bind(model.offer)
    }

    class OfferVh(view: OfferView) : BindableViewHolder<OfferModel, OfferView>(view)
}

