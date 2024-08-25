package tech.kekulta.jobfinder.presentation.ui.recycler.base

import androidx.recyclerview.widget.RecyclerView

abstract class BindableDelegateAdapter<M, in VH>(modelClasses: Set<Class<out M>>) :
    DelegateAdapter<M, VH>(modelClasses) where VH : RecyclerView.ViewHolder, VH : Bindable<M> {
    constructor(modelClass: Class<out M>) : this(setOf(modelClass))

    override fun bind(model: M, viewHolder: VH) {
        viewHolder.bind(model)
    }
}