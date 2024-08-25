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
import tech.kekulta.uikit.ButtonSmall3

class ButtonSmall3Adapter :
    BindableDelegateAdapter<ButtonItem.Small3, ButtonSmall3Adapter.ButtonVh>(ButtonItem.Small3::class.java),
    EventDispatcher<UiEvent> by UiEventDispatcher() {

    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ButtonVh(ButtonSmall3(parent.context).also {
            it.setLayoutParams(
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                )
            )
        }).also { it.setParent(this) }

    class ButtonVh(private val view: ButtonSmall3) : RecyclerView.ViewHolder(view),
        Bindable<ButtonItem.Small3>, EventDispatcher<UiEvent> by UiEventDispatcher() {
        override fun bind(model: ButtonItem.Small3) {
            view.setOnClickListener { model.onClick?.invoke(this@ButtonVh) }
            view.isEnabled = model.isEnabled
            view.text = model.text
        }
    }
}