package tech.kekulta.navigation

import kotlinx.serialization.Serializable

@Serializable
data class BackStackHolder<D : Enum<D>>(
    val priority: Int, val backstack: List<D>
)