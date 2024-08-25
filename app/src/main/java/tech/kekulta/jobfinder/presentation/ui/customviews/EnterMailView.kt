package tech.kekulta.jobfinder.presentation.ui.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import tech.kekulta.jobfinder.R
import tech.kekulta.jobfinder.databinding.EnterMailViewBinding

class EnterMailView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : ConstraintLayout(context, attrs, R.attr.enterMailViewStyle) {
    private val binding = EnterMailViewBinding.inflate(LayoutInflater.from(context), this)
    private var emailListener: ((email: String) -> Unit)? = null

    init {
        binding.ok.setOnClickListener { binding.input.validateInput() }
        binding.input.setOnTextChangedListener { binding.ok.isEnabled = it.isNotEmpty() }
        binding.input.setOnValidListener { email, isValid ->
            if (isValid) {
                emailListener?.invoke(email)
            }
        }

        binding.okWithPassword.setOnClickListener {
            Toast.makeText(context, "Ok with password!", Toast.LENGTH_SHORT).show()
        }
    }

    fun setEmailListener(listener: ((email: String) -> Unit)?) {
        emailListener = listener
    }
}
