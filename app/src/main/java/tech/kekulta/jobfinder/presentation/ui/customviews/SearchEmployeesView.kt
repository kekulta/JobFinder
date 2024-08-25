package tech.kekulta.jobfinder.presentation.ui.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import tech.kekulta.jobfinder.R
import tech.kekulta.jobfinder.databinding.SearchEmployeesViewBinding

class SearchEmployeesView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : ConstraintLayout(context, attrs, R.attr.enterMailViewStyle) {
    private val binding = SearchEmployeesViewBinding.inflate(LayoutInflater.from(context), this)
}
