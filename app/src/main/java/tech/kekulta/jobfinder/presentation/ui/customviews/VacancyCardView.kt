package tech.kekulta.jobfinder.presentation.ui.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import kotlinx.datetime.LocalDate
import tech.kekulta.jobfinder.R
import tech.kekulta.jobfinder.databinding.VacancyCardViewBinding
import tech.kekulta.jobfinder.domain.models.VacancyId
import tech.kekulta.jobfinder.domain.models.VacancyModel
import tech.kekulta.jobfinder.presentation.ui.events.DislikeVacancyPressed
import tech.kekulta.jobfinder.presentation.ui.events.EventDispatcher
import tech.kekulta.jobfinder.presentation.ui.events.LikeVacancyPressed
import tech.kekulta.jobfinder.presentation.ui.events.ApplyPressed
import tech.kekulta.jobfinder.presentation.ui.events.UiEvent
import tech.kekulta.jobfinder.presentation.ui.events.UiEventDispatcher
import tech.kekulta.jobfinder.presentation.ui.events.VacancyPressed
import tech.kekulta.jobfinder.presentation.ui.recycler.base.Bindable
import tech.kekulta.uikit.gone
import tech.kekulta.uikit.show

class VacancyCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : ConstraintLayout(context, attrs, R.attr.vacancyCardViewStyle), Bindable<VacancyModel>,
    EventDispatcher<UiEvent> by UiEventDispatcher() {
    private val binding = VacancyCardViewBinding.inflate(LayoutInflater.from(context), this)
    private var id: VacancyId? = null
    private var isChecked = false

    init {
        binding.like.setOnClickListener {
            id?.let { id ->
                dispatch(if (isChecked) DislikeVacancyPressed(id) else LikeVacancyPressed(id))
            }
        }

        binding.apply.setOnClickListener {
            id?.let { id ->
                dispatch(ApplyPressed(id))
            }
        }

        binding.root.setOnClickListener {
            id?.let { id ->
                dispatch(VacancyPressed(id))
            }
        }
    }

    override fun bind(model: VacancyModel) {
        with(binding) {
            id = model.id
            if (model.lookingNumber == 0) {
                val constraintLayout = this@VacancyCardView
                val constraintSet = ConstraintSet()
                constraintSet.clone(constraintLayout)
                constraintSet.connect(
                    R.id.like, ConstraintSet.START, R.id.title, ConstraintSet.END, 0
                )
                constraintSet.connect(
                    R.id.title, ConstraintSet.END, R.id.like, ConstraintSet.START, 0
                )
                constraintSet.applyTo(constraintLayout)

                lookingNumber.gone()
            } else {
                val constraintLayout = this@VacancyCardView
                val constraintSet = ConstraintSet()
                constraintSet.clone(constraintLayout)
                constraintSet.connect(
                    R.id.like, ConstraintSet.START, R.id.lookingNumber, ConstraintSet.END, 0
                )
                constraintSet.connect(
                    R.id.lookingNumber, ConstraintSet.END, R.id.like, ConstraintSet.START, 0
                )

                constraintSet.applyTo(constraintLayout)
                lookingNumber.show()
                lookingNumber.text = resources.getQuantityString(
                    R.plurals.looking_number,
                    model.lookingNumber,
                    model.lookingNumber,
                )
            }
            if(model.salary.short != null) {
                salary.text = model.salary.short
                salary.show()
            } else {
                salary.gone()
            }
            title.text = model.title
            city.text = model.address.city
            company.text = model.companyName
            experience.text = model.experience.preview
            publishedDate.text = formatDate(model.publishedDate)
            setLike(model.isFavorite)
        }
    }

    private fun setLike(isChecked: Boolean) {
        this.isChecked = isChecked
        binding.like.isChecked = isChecked
    }

    private fun formatDate(date: LocalDate): String {
        val template = MonthTemplates[date.monthNumber - 1]
        return resources.getQuantityString(template, date.dayOfMonth, date.dayOfMonth)
    }

    companion object {
        private val MonthTemplates = listOf(
            R.plurals.published_january,
            R.plurals.published_february,
            R.plurals.published_march,
            R.plurals.published_april,
            R.plurals.published_may,
            R.plurals.published_june,
            R.plurals.published_july,
            R.plurals.published_august,
            R.plurals.published_september,
            R.plurals.published_october,
            R.plurals.published_november,
            R.plurals.published_december,
        )
    }
}