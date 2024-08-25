package tech.kekulta.jobfinder.domain.models

data class OfferModel(
    val id: OfferId?,
    val title: String,
    val link: String,
    val button: OfferButton?,
)
