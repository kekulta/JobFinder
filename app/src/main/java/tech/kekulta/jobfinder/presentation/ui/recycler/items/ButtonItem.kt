package tech.kekulta.jobfinder.presentation.ui.recycler.items

import tech.kekulta.jobfinder.presentation.ui.events.EventDispatcher
import tech.kekulta.jobfinder.presentation.ui.events.UiEvent
import tech.kekulta.jobfinder.presentation.ui.recycler.base.DelegateAdapterItem

sealed class ButtonItem(
    open val id: String,
    open val text: String,
    open val isEnabled: Boolean = true,
    open val onClick: ((EventDispatcher<UiEvent>) -> Unit)? = null
) : DelegateAdapterItem {
    override fun id() = id
    override fun content() = this

    data class Big1(
        override val id: String,
        override val text: String,
        override val isEnabled: Boolean = true,
        override val onClick: ((EventDispatcher<UiEvent>) -> Unit)? = null
    ) : ButtonItem(id, text, isEnabled, onClick)

    data class Big2(
        override val id: String,
        override val text: String,
        override val isEnabled: Boolean = true,
        override val onClick: ((EventDispatcher<UiEvent>) -> Unit)? = null
    ) : ButtonItem(id, text, isEnabled, onClick)

    data class Small3(
        override val id: String,
        override val text: String,
        override val isEnabled: Boolean = true,
        override val onClick: ((EventDispatcher<UiEvent>) -> Unit)? = null
    ) : ButtonItem(id, text, isEnabled, onClick)
}

