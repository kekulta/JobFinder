package tech.kekulta.jobfinder.presentation.ui.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import tech.kekulta.jobfinder.R
import tech.kekulta.jobfinder.databinding.LocationCardViewBinding
import tech.kekulta.jobfinder.domain.models.Address
import tech.kekulta.uikit.dimen
import tech.kekulta.uikit.R as uikit

class LocationCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : LinearLayout(context, attrs, R.attr.locationCardViewStyle) {
    private val binding = LocationCardViewBinding.inflate(LayoutInflater.from(context), this)

    init {
        Glide.with(this).load(R.drawable.map_image).transform(RoundedCorners(dimen(uikit.dimen.size_x16)))
            .into(binding.map)
    }

    fun setCompany(name: String) {
        binding.title.text = name
    }

    fun setLocation(location: Address) {
        binding.location.text = listOfNotNull(
            location.city,
            location.street,
            location.house
        ).joinToString(separator = ", ")
    }
}