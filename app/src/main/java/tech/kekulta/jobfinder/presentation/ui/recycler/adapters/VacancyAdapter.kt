package tech.kekulta.jobfinder.presentation.ui.recycler.adapters

import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import tech.kekulta.jobfinder.domain.models.VacancyModel
import tech.kekulta.jobfinder.presentation.ui.customviews.VacancyCardView
import tech.kekulta.jobfinder.presentation.ui.events.EventDispatcher
import tech.kekulta.jobfinder.presentation.ui.events.UiEvent
import tech.kekulta.jobfinder.presentation.ui.events.UiEventDispatcher
import tech.kekulta.jobfinder.presentation.ui.recycler.base.BindableViewHolder
import tech.kekulta.jobfinder.presentation.ui.recycler.base.DelegateAdapter
import tech.kekulta.jobfinder.presentation.ui.recycler.items.VacancyItem

class VacancyAdapter :
    DelegateAdapter<VacancyItem, VacancyAdapter.VacancyVh>(VacancyItem::class.java),
    EventDispatcher<UiEvent> by UiEventDispatcher() {

    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        VacancyVh(VacancyCardView(parent.context).also {
            it.setLayoutParams(
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                )
            )
            it.setParent(this)
        })

    override fun bind(model: VacancyItem, viewHolder: VacancyVh) {
        viewHolder.bind(model.model)
    }

    class VacancyVh(view: VacancyCardView) : BindableViewHolder<VacancyModel, VacancyCardView>(view)
}

