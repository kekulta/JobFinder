package tech.kekulta.jobfinder.data.mappers

import kotlinx.datetime.LocalDate
import tech.kekulta.jobfinder.data.models.VacancyDto
import tech.kekulta.jobfinder.domain.models.Address
import tech.kekulta.jobfinder.domain.models.Experience
import tech.kekulta.jobfinder.domain.models.Salary
import tech.kekulta.jobfinder.domain.models.Schedule
import tech.kekulta.jobfinder.domain.models.VacancyId
import tech.kekulta.jobfinder.domain.models.VacancyModel

class VacancyDtoToVacancyModelMapper {
    fun map(dto: VacancyDto): VacancyModel? {

        return VacancyModel(
            id = dto.id?.let { VacancyId(it) } ?: return null,
            lookingNumber = dto.lookingNumber ?: 0,
            title = dto.title ?: return null,
            address = Address(
                city = dto.address?.get("town") ?: return null,
                street = dto.address["street"],
                house = dto.address["house"],
            ),
            companyName = dto.company ?: return null,
            experience = Experience(
                preview = dto.experience?.get("previewText") ?: return null,
                text = dto.experience["text"] ?: return null,
            ),
            publishedDate = dto.publishedDate?.let { LocalDate.parse(it, format) } ?: return null,
            isFavorite = dto.isFavorite ?: false,
            salary = Salary(
                full = dto.salary?.get("full") ?: return null,
                short = dto.salary["short"],
            ),
            schedules = dto.schedules?.let { parseSchedules(it) } ?: emptyList(),
            appliedNumber = dto.appliedNumber ?: 0,
            description = dto.description,
            responsibilities = dto.responsibilities,
            questions = dto.questions ?: emptyList(),
        )
    }

    fun map(dto: List<VacancyDto>): List<VacancyModel> {
        return dto.mapNotNull { map(it) }
    }

    private fun parseSchedules(schedules: List<String>): List<Schedule> {
        return buildList {
            schedules.forEach {
                when (it) {
                    "полная занятость" -> add(Schedule.FULL_TIME)
                    "полный день" -> add(Schedule.FULL_DAY)
                    "частичная занятость" -> add(Schedule.PART_TIME)
                    "удаленная работа" -> add(Schedule.REMOTE)
                }
            }
        }
    }

    companion object {
        private val format = LocalDate.Format {
            year()
            chars("-")
            monthNumber()
            chars("-")
            dayOfMonth()
        }
    }
}