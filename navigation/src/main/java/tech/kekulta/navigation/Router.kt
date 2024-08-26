package tech.kekulta.navigation

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit

class Router<D : Enum<D>>(
    private val fragmentManager: FragmentManager,
    private val container: Int,
    private val onEmptyStack: () -> Unit,
    private val fragmentFactory: (D) -> Class<out Fragment>,
    private val dialogFactory: (D) -> DialogFragment,
) {
    private var backStacks = mutableListOf<BackStackHolder<D>>()

    fun openDialog(destination: D, args: Bundle? = null) {
        val dialog = dialogFactory(destination)
        dialog.arguments = args

        dialog.show(fragmentManager, "TAG")
    }

    fun navigate(destination: D, args: Bundle? = null, animations: Animations? = null) {
        fragmentManager.commit {
            setReorderingAllowed(true)
            addToBackStack(null)
            animations?.let {
                setCustomAnimations(
                    animations.enter,
                    animations.exit,
                    animations.popEnter,
                    animations.popExit,
                )
            }
            replace(container, fragmentFactory(destination), args, null)
        }

        addToCurrentBackStack(destination)
    }

    fun setRoot(root: D, clearRootBackStack: Boolean = false, args: Bundle? = null) {
        if (getCurrentBackStackOrNull() != null && root == getCurrentRoot()) return

        fragmentManager.commit {
            setReorderingAllowed(true)
            getCurrentBackStackOrNull()?.let { _ ->
                fragmentManager.saveBackStack(getCurrentRoot().name)
            }

            if (isRootSaved(root)) {
                restoreRoot(root)
                fragmentManager.restoreBackStack(root.name)
            } else {
                createRoot(root)
                addToBackStack(root.name)
                replace(container, fragmentFactory(root), args, null)
            }

            if (clearRootBackStack) {
                removeOtherBackStacks()
            }
        }
    }

    fun goBack() {
        if (getCurrentBackStack().backstack.size <= 1) {
            if (backStacks.size == 1) {
                onEmptyStack()
                return
            }

            fragmentManager.commit {
                setReorderingAllowed(true)

                fragmentManager.popBackStack(
                    getCurrentRoot().name, FragmentManager.POP_BACK_STACK_INCLUSIVE
                )
                removeCurrentBackStack()

                if (getCurrentBackStackOrNull() == null) {
                    onEmptyStack()
                    return
                }

                fragmentManager.restoreBackStack(getCurrentRoot().name)
            }
        } else {
            popFromCurrentBackStack()
            fragmentManager.popBackStack()
        }
    }

    fun restoreState(state: NavState<D>) {
        backStacks = state.backStacks.toMutableList()
    }

    fun saveState(): NavState<D> {
        return NavState(backStacks)
    }

    fun getCurrentRootOrNull(): D? {
        return getCurrentBackStackOrNull()?.backstack?.firstOrNull()
    }

    fun getCurrentDestinationOrNull(): D? {
        return getCurrentBackStackOrNull()?.backstack?.lastOrNull()
    }

    private fun getCurrentBackStackOrNull(): BackStackHolder<D>? {
        return backStacks.maxByOrNull { it.priority }
    }

    private fun getCurrentBackStack(): BackStackHolder<D> {
        return backStacks.maxByOrNull { it.priority }
            ?: throw IllegalStateException("No available back stacks! Must set root first.")
    }

    private fun addToCurrentBackStack(destination: D) {
        val currentBackStack = getCurrentBackStack()
        backStacks.removeIf { it.priority == currentBackStack.priority }
        backStacks.add(currentBackStack.copy(backstack = currentBackStack.backstack + destination))
    }

    private fun popFromCurrentBackStack() {
        val currentBackStack = getCurrentBackStack()
        backStacks.removeIf { it.priority == currentBackStack.priority }
        backStacks.add(currentBackStack.copy(backstack = currentBackStack.backstack.dropLast(1)))
    }

    private fun removeOtherBackStacks() {
        val currentBackStack = getCurrentBackStack()
        backStacks.clear()
        backStacks.add(currentBackStack)
    }

    private fun removeCurrentBackStack() {
        val currentBackStack = getCurrentBackStack()
        backStacks.removeIf { it.priority == currentBackStack.priority }
    }

    private fun getCurrentRoot(): D {
        return getCurrentBackStack().backstack.firstOrNull()
            ?: throw IllegalStateException("Root must be set in backstack!")
    }

    private fun isRootSaved(root: D): Boolean {
        return backStacks.any { it.backstack.first() == root }
    }

    private fun restoreRoot(root: D) {
        val restoredBackStack = backStacks.first { it.backstack.first() == root }
        backStacks.removeIf { it.priority == restoredBackStack.priority }
        val maxPriority = backStacks.maxBy { it.priority }.priority
        backStacks.add(restoredBackStack.copy(priority = maxPriority + 1))
    }

    private fun createRoot(root: D) {
        val maxPriority = backStacks.maxByOrNull { it.priority }?.priority ?: 0
        backStacks.add(BackStackHolder(maxPriority + 1, listOf(root)))
    }
}