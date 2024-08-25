package tech.kekulta.jobfinder.presentation.ui.recycler.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BindableViewHolder<M, V>(private val view: V) : RecyclerView.ViewHolder(view),
    Bindable<M> where V : View, V : Bindable<M> {
    override fun bind(model: M) {
        view.bind(model)
    }
}