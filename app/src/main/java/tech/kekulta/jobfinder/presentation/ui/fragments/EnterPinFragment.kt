package tech.kekulta.jobfinder.presentation.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import tech.kekulta.jobfinder.R
import tech.kekulta.jobfinder.databinding.FragmentEnterPinBinding
import tech.kekulta.jobfinder.presentation.ui.events.interceptBackPressed
import tech.kekulta.jobfinder.presentation.viewmodels.EnterPinViewModel
import tech.kekulta.uikit.handleSystemBar

class EnterPinFragment : Fragment(R.layout.fragment_enter_pin) {
    private val binding by viewBinding(FragmentEnterPinBinding::bind)
    private val viewModel by viewModel<EnterPinViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        interceptBackPressed(viewModel)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleSystemBar(binding.main)
        binding.pinInput.setOnInputListener { pin ->
            viewModel.enterPin(pin)
        }

        lifecycleScope.launch {
            viewModel.observeStatus().collect {
                binding.pinInput.bind(it ?: "...")
            }
        }
    }
}