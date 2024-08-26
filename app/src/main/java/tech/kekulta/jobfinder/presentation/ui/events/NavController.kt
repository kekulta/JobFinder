package tech.kekulta.jobfinder.presentation.ui.events

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import logcat.logcat
import tech.kekulta.navigation.DestNavigator


class NavController(private val navigator: DestNavigator, private val context: Context) :
    NavEventDispatcher() {
    init {
        super.setHandle {
            when (it) {
                is SetRoot -> navigator.setRoot(it.destination, true, it.args)
                is NavigateToRoot -> navigator.setRoot(it.destination, false, it.args)
                is NavigateBack -> navigator.goBack()
                is NavigateTo -> navigator.navigate(it.destination, it.args, it.animations)
                is OpenLink -> openLink(it.link).let { true }
                is OpenDialog -> navigator.openDialog(it.destination, it.args)
            }
        }
    }

    override fun setHandle(handle: EventHandle<NavEvent>?) {
        throw UnsupportedOperationException()
    }

    private fun openLink(link: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        logcat { "Opening link in browser: $link" }
        startActivity(context, browserIntent, null)
    }
}