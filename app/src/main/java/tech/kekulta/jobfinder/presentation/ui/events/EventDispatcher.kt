package tech.kekulta.jobfinder.presentation.ui.events

interface EventDispatcher<E> {
    fun setParent(dispatcher: EventDispatcher<E>?)
    fun setHandle(handle: EventHandle<E>?)
    fun dispatch(event: E)
}