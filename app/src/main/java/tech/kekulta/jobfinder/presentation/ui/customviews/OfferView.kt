package tech.kekulta.jobfinder.presentation.ui.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import tech.kekulta.jobfinder.R
import tech.kekulta.jobfinder.databinding.OfferViewBinding
import tech.kekulta.jobfinder.domain.models.OfferId
import tech.kekulta.jobfinder.domain.models.OfferModel
import tech.kekulta.jobfinder.presentation.ui.events.EventDispatcher
import tech.kekulta.jobfinder.presentation.ui.events.LinkPressed
import tech.kekulta.jobfinder.presentation.ui.events.UiEvent
import tech.kekulta.jobfinder.presentation.ui.events.UiEventDispatcher
import tech.kekulta.jobfinder.presentation.ui.recycler.base.Bindable
import tech.kekulta.uikit.gone
import tech.kekulta.uikit.hide
import tech.kekulta.uikit.show

class OfferView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : LinearLayout(context, attrs, tech.kekulta.uikit.R.attr.offerViewStyle), Bindable<OfferModel>,
    EventDispatcher<UiEvent> by UiEventDispatcher() {
    private val binding = OfferViewBinding.inflate(LayoutInflater.from(context), this)

    override fun bind(model: OfferModel) {
        binding.root.setOnClickListener {
            dispatch(LinkPressed(model.link))
        }

        binding.title.text = model.title

        val icon = getIcon(model.id)
        if (icon == null) {
            binding.icon.hide()
        } else {
            binding.icon.setImageResource(icon)
            binding.icon.show()
        }

        if (model.button == null) {
            binding.title.maxLines = 3
            binding.title.minLines = 3
            binding.button.gone()
        } else {
            binding.title.maxLines = 2
            binding.title.minLines = 2
            binding.button.text = model.button.text
            binding.button.show()
        }
    }

    @DrawableRes
    private fun getIcon(id: OfferId?): Int? {
        return when (id?.id) {
            "near_vacancies" -> R.drawable.near
            "level_up_resume" -> R.drawable.level_up
            "temporary_job" -> R.drawable.temporary
            else -> null
        }
    }
}

