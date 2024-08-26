package tech.kekulta.jobfinder.presentation.ui.fragments

import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import tech.kekulta.jobfinder.R
import tech.kekulta.jobfinder.databinding.BottomSheetApplicationBinding
import tech.kekulta.jobfinder.domain.models.VacancyId
import tech.kekulta.jobfinder.presentation.ui.events.AddCover
import tech.kekulta.jobfinder.presentation.ui.events.BackPressed
import tech.kekulta.jobfinder.presentation.ui.events.ApplyPressed
import tech.kekulta.jobfinder.presentation.ui.events.interceptBackPressed
import tech.kekulta.jobfinder.presentation.viewmodels.application.ApplicationEvent
import tech.kekulta.jobfinder.presentation.viewmodels.application.ApplicationState
import tech.kekulta.jobfinder.presentation.viewmodels.application.ApplicationViewModel
import tech.kekulta.navigation.DestArgs
import tech.kekulta.uikit.gone
import tech.kekulta.uikit.hide
import tech.kekulta.uikit.show


class ApplicationBottomSheet :
    BottomSheetDialogFragment(R.layout.bottom_sheet_application) {
    private val binding by viewBinding(BottomSheetApplicationBinding::bind)
    private val viewModel by viewModel<ApplicationViewModel>()
    private var id: VacancyId? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = arguments?.getString(DestArgs.VACANCY_ID)?.let { VacancyId(it) }
        id?.let { viewModel.setId(it) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        interceptBackPressed(viewModel)

        val id = this.id
        if (id == null) {
            binding.container.hide()
            binding.indicator.show()
            viewModel.dispatch(BackPressed)
            return
        }

        Glide.with(this).load(R.drawable.avatar_icon).circleCrop().into(binding.avatar)

        binding.addCover.setOnClickListener { viewModel.dispatch(AddCover) }
        binding.apply.setOnClickListener { viewModel.dispatch(ApplyPressed(id)) }

        lifecycleScope.launch {
            viewModel.observeEvents().collect { event ->
                when (event) {
                    is ApplicationEvent.CloseDialog -> dialog?.cancel()
                }
            }
        }

        lifecycleScope.launch {
            viewModel.observeState().collect { state ->
                when (state) {
                    ApplicationState.Loading -> {
                        binding.container.hide()
                        binding.input.text.clear()
                        binding.indicator.show()
                    }

                    is ApplicationState.NoCover -> {
                        binding.title.text = state.title
                        binding.input.text.clear()
                        binding.container.show()
                        binding.indicator.hide()

                        binding.addCover.show()
                        binding.input.gone()
                    }

                    is ApplicationState.OpenCover -> {
                        binding.title.text = state.title
                        binding.container.show()
                        binding.indicator.hide()

                        binding.addCover.gone()
                        binding.input.show()
                        binding.input.requestFocus()
                        val imm = getSystemService(requireContext(), InputMethodManager::class.java)
                        imm?.showSoftInput(binding.input, InputMethodManager.SHOW_IMPLICIT)
                    }
                }
            }
        }
    }
}
