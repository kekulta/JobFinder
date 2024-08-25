package tech.kekulta.jobfinder.presentation.ui.events

import logcat.logcat

abstract class AbstractEventDispatcher<E> : EventDispatcher<E> {
    private var parent: EventDispatcher<E>? = null
    private var handle: EventHandle<E>? = null

    override fun setParent(dispatcher: EventDispatcher<E>?) {
        parent = dispatcher
    }

    override fun setHandle(handle: EventHandle<E>?) {
        this.handle = handle
    }

    override fun dispatch(event: E) {
        if (handle?.handle(event) == true) {
            logcat { "Handled event: $event" }
        } else {
            parent?.dispatch(event) ?: logcat { "Unhandled event: $event" }
        }
    }
}