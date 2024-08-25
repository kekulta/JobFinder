package tech.kekulta.uikit

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.checkbox.MaterialCheckBox

class LikeCheckbox @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : MaterialCheckBox(context, attrs, R.attr.likeCheckboxStyle)
