package tech.kekulta.jobfinder.presentation.ui.events

fun interface EventHandle<E> {
    fun handle(event: E): Boolean
}