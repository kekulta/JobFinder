package tech.kekulta.jobfinder.presentation.ui.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import tech.kekulta.jobfinder.R
import tech.kekulta.jobfinder.databinding.SearchBlockViewBinding
import tech.kekulta.jobfinder.presentation.ui.events.BackPressed
import tech.kekulta.jobfinder.presentation.ui.events.EventDispatcher
import tech.kekulta.jobfinder.presentation.ui.events.UiEvent
import tech.kekulta.jobfinder.presentation.ui.events.UiEventDispatcher
import tech.kekulta.jobfinder.presentation.ui.recycler.base.Bindable
import tech.kekulta.jobfinder.presentation.ui.recycler.items.SearchBlockItem
import tech.kekulta.uikit.gone
import tech.kekulta.uikit.show
import tech.kekulta.uikit.R as uikit

class SearchBlockView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : ConstraintLayout(context, attrs, 0), Bindable<SearchBlockItem>,
    EventDispatcher<UiEvent> by UiEventDispatcher() {
    private val binding = SearchBlockViewBinding.inflate(LayoutInflater.from(context), this)

    override fun bind(model: SearchBlockItem) {
        binding.input.hint = model.hint

        when (model) {
            is SearchBlockItem.Search -> {
                binding.input.setOnIconPressedListener {
                    dispatch(BackPressed)
                }
                binding.input.setIcon(uikit.drawable.back_arrow)
                binding.filters.show()
                binding.vacanciesCount.show()

                binding.vacanciesCount.text =
                    resources.getQuantityString(
                        R.plurals.vacancies_number,
                        model.foundCount,
                        model.foundCount
                    )
            }

            is SearchBlockItem.Idle -> {
                binding.input.setIcon(uikit.drawable.search_icon)
                binding.filters.gone()
                binding.vacanciesCount.gone()
            }
        }
    }
}
