package tech.kekulta.uikit

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import android.widget.LinearLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.addTextChangedListener
import tech.kekulta.uikit.databinding.MailInputFieldBinding

class MailInputField @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : LinearLayout(context, attrs, R.attr.inputFieldStyle) {
    private val binding = MailInputFieldBinding.inflate(LayoutInflater.from(context), this)
    private var onTextChangedListener: ((input: String) -> Unit)? = null
    private var onValidListener: ((email: String, isValid: Boolean) -> Unit)? = null

    init {
        binding.clearButton.setOnClickListener {
            with(binding.editText) {
                text.clear()
            }
        }

        binding.editText.addTextChangedListener(onTextChanged = { s, _, _, _ ->
            if (s.isNullOrEmpty()) {
                binding.mailIcon.show()
                binding.clearButton.gone()
            } else {
                binding.mailIcon.gone()
                binding.clearButton.show()
            }
            onTextChangedListener?.invoke(s.toString())
            setErrorState(false)
        })

        binding.editText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                validateInput()
                true
            } else {
                false
            }
        }
    }

    fun setOnTextChangedListener(listener: ((input: String) -> Unit)?) {
        onTextChangedListener = listener
    }

    fun setOnValidListener(listener: ((email: String, isValid: Boolean) -> Unit)?) {
        onValidListener = listener
    }

    fun validateInput() {
        val email = binding.editText.text.toString()
        val isValid = email.isValidEmail()
        onValidListener?.invoke(email, isValid)
        setErrorState(!isValid)
    }

    private fun setErrorState(isError: Boolean) {
        if (isError) {
            binding.inputField.foreground =
                ResourcesCompat.getDrawable(resources, R.drawable.border_round_low, context.theme)
            binding.errorMessage.show()
        } else {
            binding.inputField.foreground = null
            binding.errorMessage.gone()
        }
    }
}
