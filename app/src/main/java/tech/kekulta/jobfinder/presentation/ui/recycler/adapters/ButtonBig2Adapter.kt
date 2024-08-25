package tech.kekulta.jobfinder.presentation.ui.recycler.adapters

import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import tech.kekulta.jobfinder.presentation.ui.events.EventDispatcher
import tech.kekulta.jobfinder.presentation.ui.events.UiEvent
import tech.kekulta.jobfinder.presentation.ui.events.UiEventDispatcher
import tech.kekulta.jobfinder.presentation.ui.recycler.base.Bindable
import tech.kekulta.jobfinder.presentation.ui.recycler.base.BindableDelegateAdapter
import tech.kekulta.jobfinder.presentation.ui.recycler.items.ButtonItem
import tech.kekulta.uikit.ButtonBig2

class ButtonBig2Adapter :
    BindableDelegateAdapter<ButtonItem.Big2, ButtonBig2Adapter.ButtonVh>(ButtonItem.Big2::class.java),
    EventDispatcher<UiEvent> by UiEventDispatcher() {

    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ButtonVh(ButtonBig2(parent.context).also {
            it.setLayoutParams(
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                )
            )
        }).also { it.setParent(this) }

    class ButtonVh(private val view: ButtonBig2) : RecyclerView.ViewHolder(view),
        Bindable<ButtonItem.Big2>, EventDispatcher<UiEvent> by UiEventDispatcher() {
        override fun bind(model: ButtonItem.Big2) {
            view.setOnClickListener { model.onClick?.invoke(this@ButtonVh) }
            view.isEnabled = model.isEnabled
            view.text = model.text
        }
    }
}