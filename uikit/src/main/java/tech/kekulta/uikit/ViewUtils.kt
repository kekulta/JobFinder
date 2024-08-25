package tech.kekulta.uikit

import android.content.Context
import android.util.Patterns
import android.util.TypedValue
import android.view.View
import androidx.annotation.Px
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
fun Context.dp(dp: Int): Int = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), resources.displayMetrics
).toInt()

@Px
fun View.dp(dp: Int): Int = context.dp(dp)

@Px
fun Fragment.dp(dp: Int): Int = requireContext().dp(dp)

fun CharSequence?.isValidEmail() =
    !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
