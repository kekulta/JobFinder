package tech.kekulta.jobfinder.presentation.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import tech.kekulta.jobfinder.R
import tech.kekulta.jobfinder.databinding.FragmentEnterMailBinding
import tech.kekulta.jobfinder.presentation.ui.events.interceptBackPressed
import tech.kekulta.jobfinder.presentation.viewmodels.EnterMailViewModel
import tech.kekulta.uikit.handleSystemBar

class EnterMailFragment : Fragment(R.layout.fragment_enter_mail) {
    private val binding by viewBinding(FragmentEnterMailBinding::bind)
    private val viewModel by viewModel<EnterMailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        interceptBackPressed(viewModel)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleSystemBar(binding.main)
        binding.enterEmail.setEmailListener { email ->
            viewModel.enterEmail(email)
        }
    }
}