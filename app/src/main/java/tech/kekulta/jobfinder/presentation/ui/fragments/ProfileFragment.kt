package tech.kekulta.jobfinder.presentation.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel
import tech.kekulta.jobfinder.R
import tech.kekulta.jobfinder.databinding.FragmentPlaceholderBinding
import tech.kekulta.jobfinder.presentation.ui.events.BackPressed
import tech.kekulta.jobfinder.presentation.ui.events.interceptBackPressed
import tech.kekulta.jobfinder.presentation.viewmodels.ProfileViewModel
import tech.kekulta.uikit.handleSystemBar

class ProfileFragment : Fragment(R.layout.fragment_placeholder) {
    private val binding by viewBinding(FragmentPlaceholderBinding::bind)
    private val viewModel by viewModel<ProfileViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        interceptBackPressed(viewModel)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleSystemBar(binding.main)
        Glide.with(this).load(R.drawable.avatar_icon).circleCrop().into(binding.avatar)
        binding.backButton.setOnClickListener { viewModel.dispatch(BackPressed) }
    }
}