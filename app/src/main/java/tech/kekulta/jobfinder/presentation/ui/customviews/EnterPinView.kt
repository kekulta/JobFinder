package tech.kekulta.jobfinder.presentation.ui.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import tech.kekulta.jobfinder.R
import tech.kekulta.jobfinder.databinding.EnterPinViewBinding

class EnterPinView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : LinearLayout(context, attrs, R.attr.enterPinViewStyle) {
    private val binding = EnterPinViewBinding.inflate(LayoutInflater.from(context), this)
    private var onInputListener: ((pin: String) -> Unit)? = null

    init {
        binding.input.setOnPinChangedListener {
            binding.ok.isEnabled = it.length == 4
        }

        binding.ok.setOnClickListener {
            onInputListener?.invoke(binding.input.getPin())
        }

        binding.input.setOnDoneListener { onInputListener?.invoke(it) }
    }

    fun setOnInputListener(listener: ((pin: String) -> Unit)?) {
        onInputListener = listener
    }

    fun bind(email: String) {
        binding.title.text = context.getString(R.string.send_to_to_mail, email)
    }
}
