package tech.kekulta.navigation

import kotlinx.serialization.Serializable

@Serializable
data class NavState<D : Enum<D>>(
    val backStacks: List<BackStackHolder<D>>,
)