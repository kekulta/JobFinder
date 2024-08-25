package tech.kekulta.jobfinder.data.mappers

import tech.kekulta.jobfinder.data.models.OfferDto
import tech.kekulta.jobfinder.domain.models.OfferButton
import tech.kekulta.jobfinder.domain.models.OfferId
import tech.kekulta.jobfinder.domain.models.OfferModel

class OfferDtoToOfferModelMapper {
    fun map(dto: OfferDto): OfferModel? {

        return OfferModel(
            title = dto.title ?: return null,
            link = dto.link ?: return null,
            id = dto.id?.let { OfferId(it) },
            button = dto.button?.get("text")?.let { OfferButton(it) },
        )
    }

    fun map(dto: List<OfferDto>): List<OfferModel> {
        return dto.mapNotNull { map(it) }
    }
}