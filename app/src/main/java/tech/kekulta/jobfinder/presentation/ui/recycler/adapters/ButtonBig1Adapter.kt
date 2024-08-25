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
import tech.kekulta.uikit.ButtonBig1

class ButtonBig1Adapter :
    BindableDelegateAdapter<ButtonItem.Big1, ButtonBig1Adapter.ButtonVh>(ButtonItem.Big1::class.java),
    EventDispatcher<UiEvent> by UiEventDispatcher() {

    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ButtonVh(ButtonBig1(parent.context).also {
            it.setLayoutParams(
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                )
            )
        }).also { it.setParent(this) }

    class ButtonVh(private val view: ButtonBig1) : RecyclerView.ViewHolder(view),
        Bindable<ButtonItem.Big1>, EventDispatcher<UiEvent> by UiEventDispatcher() {
        override fun bind(model: ButtonItem.Big1) {
            view.setOnClickListener { model.onClick?.invoke(this@ButtonVh) }
            view.isEnabled = model.isEnabled
            view.text = model.text
        }
    }
}