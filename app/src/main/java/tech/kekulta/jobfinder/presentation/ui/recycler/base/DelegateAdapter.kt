package tech.kekulta.jobfinder.presentation.ui.recycler.base

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class DelegateAdapter<M, in VH : RecyclerView.ViewHolder>(val modelClasses: Set<Class<out M>>) {
    constructor(modelClass: Class<out M>) : this(setOf(modelClass))

    abstract fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder
    abstract fun bind(model: M, viewHolder: VH)
}
