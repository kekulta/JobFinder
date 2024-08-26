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
            s?.getOrNull(0)?.toString().let {
                if (it == null) {
                    binding.s1.show()
                    binding.i1.text = ""
                } else {
                    binding.s1.hide()
                    binding.i1.text = it
                }
            }
            s?.getOrNull(1)?.toString().let {
                if (it == null) {
                    binding.s2.show()
                    binding.i2.text = ""
                } else {
                    binding.s2.hide()
                    binding.i2.text = it
                }
            }
            s?.getOrNull(2)?.toString().let {
                if (it == null) {
                    binding.s3.show()
                    binding.i3.text = ""
                } else {
                    binding.s3.hide()
                    binding.i3.text = it
                }
            }
            s?.getOrNull(3)?.toString().let {
                if (it == null) {
                    binding.s4.show()
                    binding.i4.text = ""
                } else {
                    binding.s4.hide()
                    binding.i4.text = it
                }
            }
        })

        binding.et.addTextChangedListener(onTextChanged = { s, _, _, _ -> pinListener?.invoke(s.toString()) })

        binding.et.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE && binding.et.text.length == 4) {
                doneListener?.invoke(binding.et.text.toString())
                true
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
