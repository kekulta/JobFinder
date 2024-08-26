package tech.kekulta.jobfinder.data.repositories

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

abstract class AbstractCoroutineManager {
    private val holder = mutableMapOf<Any, Job>()
    private val managerScope = CoroutineScope(Dispatchers.Main)

    fun launchScope(
        scopeId: Any,
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        keepOld: Boolean = false,
        block: suspend CoroutineScope.() -> Unit
    ) {
        if (holder[scopeId]?.isActive == true && keepOld) return

        holder[scopeId]?.cancel()
        holder[scopeId] = managerScope.launch(dispatcher) {
            block()
        }
    }

    fun cancelScope(scopeId: Any): Boolean {
        return if (holder[scopeId] == null) {
            false
        } else {
            holder[scopeId]?.cancel()
            true
        }
    }

    fun clear() {
        managerScope.cancel()
        holder.clear()
    }
}
