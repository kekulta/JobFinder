package tech.kekulta.jobfinder.presentation.ui.recycler.adapters

import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import tech.kekulta.jobfinder.presentation.ui.customviews.TitleView
import tech.kekulta.jobfinder.presentation.ui.recycler.base.BindableDelegateAdapter
import tech.kekulta.jobfinder.presentation.ui.recycler.base.BindableViewHolder
import tech.kekulta.jobfinder.presentation.ui.recycler.items.TitleItem

class TitleAdapter :
    BindableDelegateAdapter<TitleItem, TitleAdapter.TitleVh>(TitleItem::class.java) {

    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        TitleVh(TitleView(parent.context).also {
            it.setLayoutParams(
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                )
            )
        })

    class TitleVh(view: TitleView) : BindableViewHolder<TitleItem, TitleView>(view)
}