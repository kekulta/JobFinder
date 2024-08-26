package tech.kekulta.uikit

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.widget.addTextChangedListener
import tech.kekulta.uikit.databinding.InputFieldBinding


class InputField @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : LinearLayout(context, attrs, R.attr.inputFieldStyle) {
    private val binding = InputFieldBinding.inflate(LayoutInflater.from(context), this)
    private var onTextChangedListener: ((input: String) -> Unit)? = null
    private var onBackPressedListener: (() -> Unit)? = null
    private var onDoneListener: ((input: String) -> Unit)? = null

    var hint: CharSequence
        get() = binding.editText.hint
        set(value) {
            binding.editText.hint = value
        }

    init {
        binding.leadingIcon.setOnClickListener {
            onBackPressedListener?.invoke()
        }

        binding.clearButton.setOnClickListener {
            with(binding.editText) {
                text.clear()
            }
        }

        binding.editText.addTextChangedListener(onTextChanged = { s, _, _, _ ->
            if (s.isNullOrEmpty()) {
                binding.clearButton.gone()
            } else {
                binding.clearButton.show()
            }
            onTextChangedListener?.invoke(s.toString())
        })

        binding.editText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                onDoneListener?.invoke(binding.editText.text.toString())
                val imm = getSystemService(context, InputMethodManager::class.java)
                imm?.hideSoftInputFromWindow(binding.editText.windowToken, 0)
                binding.editText.clearFocus()

                true
            } else {
                false
            }
        }
    }

    fun setOnTextChangedListener(listener: ((input: String) -> Unit)?) {
        onTextChangedListener = listener
    }

    fun setOnDoneListener(listener: ((input: String) -> Unit)?) {
        onDoneListener = listener
    }

    fun setOnIconPressedListener(listener: (() -> Unit)?) {
        onBackPressedListener = listener
    }

    fun setIcon(@DrawableRes icon: Int?) {
        if (icon == null) {
            binding.leadingIcon.gone()
        } else {
            binding.leadingIcon.setImageResource(icon)
            binding.leadingIcon.show()
        }
    }
}
