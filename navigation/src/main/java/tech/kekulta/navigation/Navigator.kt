package tech.kekulta.navigation

import android.os.Bundle

open class Navigator<D : Enum<D>> {
    private var router: Router<D>? = null
    private var onNavigatedListener: ((dest: D?, root: D?) -> Unit)? = null
    private var destination: D? = null

    fun openDialog(destination: D, args: Bundle? = null): Boolean {
        router?.openDialog(destination, args) ?: return false
        onNavigated()

        return true
    }

    fun navigate(destination: D, args: Bundle? = null, animations: Animations? = null): Boolean {
        router?.navigate(destination, args, animations) ?: return false
        onNavigated()

        return true
    }

    fun setRoot(root: D, clearRootBackStack: Boolean = false, args: Bundle? = null): Boolean {
        router?.setRoot(root, clearRootBackStack, args)
            ?: return false
        onNavigated()

        return true
    }

    fun goBack(): Boolean {
        router?.goBack() ?: return false
        onNavigated()

        return true
    }

    fun setOnNavigatedListener(listener: ((dest: D?, root: D?) -> Unit)) {
        onNavigatedListener = listener
        onNavigated()
    }

    fun attachRouter(router: Router<D>) {
        this.router = router
        onNavigated()
    }

    fun detachRouter() {
        router = null
    }

    private fun onNavigated() {
        router?.let { router ->
            onNavigatedListener?.invoke(
                router.getCurrentDestinationOrNull(),
                router.getCurrentRootOrNull()
            )
        }
    }
}