package tech.kekulta.jobfinder.presentation.ui.recycler.base

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

internal class DelegateAdapterItemDiffCallback : DiffUtil.ItemCallback<DelegateAdapterItem>() {

    override fun areItemsTheSame(
        oldItem: DelegateAdapterItem,
        newItem: DelegateAdapterItem
    ): Boolean =
        oldItem.id() == newItem.id()

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(
        oldItem: DelegateAdapterItem,
        newItem: DelegateAdapterItem
    ): Boolean =
        oldItem.content() == newItem.content()
}
