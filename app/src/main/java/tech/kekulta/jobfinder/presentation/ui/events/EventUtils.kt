package tech.kekulta.jobfinder.presentation.ui.events

import androidx.activity.ComponentActivity
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner

@Suppress("FunctionName")
fun BackHandle(dispatcher: EventDispatcher<NavEvent>): EventHandle<UiEvent> {
    return EventHandle { event ->
        when (event) {
            is BackPressed -> {
                dispatcher.dispatch(NavigateBack)
                true
            }

            else -> false
        }
    }
}

fun Fragment.interceptBackPressed(dispatcher: EventDispatcher<UiEvent>) {
    requireActivity().interceptBackPressed(dispatcher, this)
}

fun ComponentActivity.interceptBackPressed(
    dispatcher: EventDispatcher<UiEvent>, owner: LifecycleOwner = this
) {
    onBackPressedDispatcher.addCallback(owner) {
        dispatcher.dispatch(BackPressed)
    }
}
