package tech.kekulta.jobfinder.data.models

import kotlinx.serialization.Serializable

@Serializable
data class OfferDto(
    val id: String? = null,
    val title: String? = null,
    val button: Map<String, String>? = null,
    val link: String? = null,
)
