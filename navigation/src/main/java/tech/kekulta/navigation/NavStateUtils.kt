package tech.kekulta.navigation

import android.os.Bundle
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

inline fun <reified D : Enum<D>> Router<D>.restoreState(state: NavState<D>?, startDestination: D) {
    if (state == null) {
        setRoot(startDestination)
    } else {
        restoreState(state)
    }
}

inline fun <reified D : Enum<D>> Bundle.getNavState(key: String): NavState<D>? {
    return this.getString(key)?.let { Json.decodeFromString<NavState<D>>(it) }
}

inline fun <reified D : Enum<D>> NavState<D>.saveToBundle(key: String, bundle: Bundle) {
    bundle.putString(key, Json.encodeToString(this))
}
