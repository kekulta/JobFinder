package tech.kekulta.jobfinder.presentation.ui.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import tech.kekulta.jobfinder.R
import tech.kekulta.jobfinder.databinding.TitleViewBinding
import tech.kekulta.jobfinder.presentation.ui.recycler.base.Bindable
import tech.kekulta.jobfinder.presentation.ui.recycler.items.TitleItem
import tech.kekulta.uikit.gone
import tech.kekulta.uikit.show

class TitleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : LinearLayout(context, attrs, R.attr.titleViewStyle), Bindable<TitleItem> {
    private val binding = TitleViewBinding.inflate(LayoutInflater.from(context), this)

    override fun bind(model: TitleItem) {
        binding.title.text = model.title
        if (model.subtitle == null) {
            binding.subtitle.gone()
        } else {
            binding.subtitle.text = model.subtitle
            binding.subtitle.show()
        }
    }
}

