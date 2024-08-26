package tech.kekulta.uikit

import android.content.Context
import android.util.Patterns
import android.view.View
import androidx.annotation.DimenRes
import androidx.annotation.Px
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment

fun View.gone() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.INVISIBLE
}

@Px
fun Context.dimen(@DimenRes dimen: Int): Int = resources.getDimension(dimen).toInt()

@Px
fun View.dimen(@DimenRes dimen: Int): Int = context.dimen(dimen)

@Px
fun Fragment.dimen(@DimenRes dimen: Int): Int = requireContext().dimen(dimen)

fun CharSequence?.isValidEmail() =
    !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun handleSystemBar(root: View) {
    ViewCompat.setOnApplyWindowInsetsListener(root) { v, insets ->
        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
        insets
    }
}