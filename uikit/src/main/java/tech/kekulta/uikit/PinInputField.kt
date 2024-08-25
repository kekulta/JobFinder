package tech.kekulta.uikit

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import tech.kekulta.uikit.databinding.PinInputFieldBinding

class PinInputField @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : ConstraintLayout(context, attrs, 0) {
    private val binding = PinInputFieldBinding.inflate(LayoutInflater.from(context), this)
    private var doneListener: ((pin: String) -> Unit)? = null
    private var pinListener: ((pin: String) -> Unit)? = null

    init {
        binding.et.addTextChangedListener(onTextChanged = { s, _, _, _ ->
            binding.i1.text = s?.getOrNull(0)?.toString() ?: ""
            binding.i2.text = s?.getOrNull(1)?.toString() ?: ""
            binding.i3.text = s?.getOrNull(2)?.toString() ?: ""
            binding.i4.text = s?.getOrNull(3)?.toString() ?: ""
        })

        binding.et.addTextChangedListener(onTextChanged = { s, _, _, _ -> pinListener?.invoke(s.toString()) })

        binding.et.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE && binding.et.text.length == 4) {
                doneListener?.invoke(binding.et.text.toString())
                true;
            } else {
                false
            }
        }

        setOnClickListener {
            setFocus(binding.et)
        }
    }

    fun getPin(): String = binding.et.text.toString()

    fun setOnDoneListener(listener: ((pin: String) -> Unit)?) {
        doneListener = listener
    }

    fun setOnPinChangedListener(listener: ((pin: String) -> Unit)?) {
        pinListener = listener
    }

    private fun setFocus(et: EditText) {
        et.requestFocus()
        val imm = context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT)
    }
}
