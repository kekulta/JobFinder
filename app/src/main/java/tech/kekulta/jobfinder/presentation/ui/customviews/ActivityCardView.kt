package tech.kekulta.jobfinder.presentation.ui.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import tech.kekulta.jobfinder.R
import tech.kekulta.jobfinder.databinding.ActivityCardViewBinding

class ActivityCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : LinearLayout(context, attrs, R.attr.activityCardViewStyle) {
    private val binding = ActivityCardViewBinding.inflate(LayoutInflater.from(context), this)

    fun setText(text: String) {
        binding.text.text = text
    }

    fun setIcon(@DrawableRes icon: Int) {
        binding.icon.setImageResource(icon)
    }
}

