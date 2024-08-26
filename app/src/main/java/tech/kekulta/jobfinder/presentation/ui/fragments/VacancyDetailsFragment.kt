package tech.kekulta.jobfinder.presentation.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import tech.kekulta.jobfinder.R
import tech.kekulta.jobfinder.databinding.FragmentVacancyDetailsBinding
import tech.kekulta.jobfinder.domain.models.Schedule
import tech.kekulta.jobfinder.domain.models.VacancyId
import tech.kekulta.jobfinder.presentation.ui.events.ApplyPressed
import tech.kekulta.jobfinder.presentation.ui.events.BackPressed
import tech.kekulta.jobfinder.presentation.ui.events.DislikeVacancyPressed
import tech.kekulta.jobfinder.presentation.ui.events.LikeVacancyPressed
import tech.kekulta.jobfinder.presentation.ui.events.interceptBackPressed
import tech.kekulta.jobfinder.presentation.ui.recycler.adapters.ButtonBig2Adapter
import tech.kekulta.jobfinder.presentation.ui.recycler.adapters.ButtonSmall3Adapter
import tech.kekulta.jobfinder.presentation.ui.recycler.base.buildCompositeAdapter
import tech.kekulta.jobfinder.presentation.ui.recycler.decorations.Margins
import tech.kekulta.jobfinder.presentation.ui.recycler.decorations.RecyclerViewMargins
import tech.kekulta.jobfinder.presentation.ui.recycler.items.ButtonItem
import tech.kekulta.jobfinder.presentation.viewmodels.details.VacancyDetailsState
import tech.kekulta.jobfinder.presentation.viewmodels.details.VacancyDetailsViewModel
import tech.kekulta.navigation.DestArgs
import tech.kekulta.uikit.dimen
import tech.kekulta.uikit.gone
import tech.kekulta.uikit.handleSystemBar
import tech.kekulta.uikit.hide
import tech.kekulta.uikit.show
import java.util.Locale
import tech.kekulta.uikit.R as uikit

class VacancyDetailsFragment : Fragment(R.layout.fragment_vacancy_details) {
    private val binding by viewBinding(FragmentVacancyDetailsBinding::bind)
    private val viewModel by viewModel<VacancyDetailsViewModel>()
    private var id: VacancyId? = null

    private val marginsDecorator by lazy {
        RecyclerViewMargins(
            adapter = recyclerAdapter,
            margins = listOf(
                Margins(marginTop = dimen(uikit.dimen.size_x8)),
                Margins(marginTop = dimen(uikit.dimen.size_x16)),
            ),
        )
    }

    private val recyclerAdapter by lazy {
        buildCompositeAdapter {
            add(ButtonSmall3Adapter().also { it.setParent(viewModel) })
            add(ButtonBig2Adapter().also { it.setParent(viewModel) })
        }
    }

    private val recyclerLayoutManager by lazy {
        LinearLayoutManager(
            requireContext(), RecyclerView.VERTICAL, false
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        interceptBackPressed(viewModel)

        id = arguments?.getString(DestArgs.VACANCY_ID)?.let { VacancyId(it) }
        id?.let { viewModel.setId(it) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleSystemBar(binding.main)
        val id = this.id
        if (id == null) {
            binding.container.hide()
            binding.containerControls.hide()
            binding.indicator.show()
            viewModel.dispatch(BackPressed)
            return
        }

        binding.backButton.setOnClickListener { viewModel.dispatch(BackPressed) }

        lifecycleScope.launch {
            viewModel.observeState().collect { state ->
                when (state) {
                    is VacancyDetailsState.Loaded -> {
                        val model = state.model
                        binding.like.isChecked = state.model.isFavorite

                        binding.like.setOnClickListener {
                            viewModel.dispatch(
                                if (model.isFavorite) {
                                    DislikeVacancyPressed(id)
                                } else {
                                    LikeVacancyPressed(id)
                                }
                            )
                        }

                        binding.title.text = model.title
                        binding.experience.text =
                            getString(R.string.required_experience, model.experience.text)
                        binding.salary.text = model.salary.full
                        binding.schedule.text = formatSchedules(model.schedules)
                        binding.location.setLocation(model.address)
                        binding.location.setCompany(model.companyName)

                        if (model.description != null) {
                            binding.description.text = model.description
                            binding.description.show()
                        } else {
                            binding.description.gone()
                        }

                        if (model.responsibilities != null) {
                            binding.responsibility.text = model.responsibilities
                            binding.responsibilityTitle.show()
                            binding.responsibility.show()
                        } else {
                            binding.responsibilityTitle.gone()
                            binding.responsibility.gone()
                        }

                        if (model.questions.isEmpty()) {
                            binding.questionsTitle.gone()
                            recyclerAdapter.submitList(listOf(applyButton()))
                        } else {
                            recyclerAdapter.submitList(buildList {
                                model.questions.forEach {
                                    add(ButtonItem.Small3(it, it))
                                }
                                add(applyButton())
                            })
                            binding.questionsTitle.show()
                        }

                        if (model.appliedNumber != 0) {
                            binding.activityApplied.setIcon(R.drawable.activity_apply)
                            binding.activityApplied.setText(
                                resources.getQuantityString(
                                    R.plurals.applied_number,
                                    model.appliedNumber,
                                    model.appliedNumber,
                                )
                            )
                            binding.activityApplied.show()
                        } else {
                            binding.activityApplied.gone()
                        }

                        if (model.lookingNumber != 0) {
                            binding.activityLooking.setIcon(R.drawable.activity_watch)
                            binding.activityLooking.setText(
                                resources.getQuantityString(
                                    R.plurals.looking_number_short,
                                    model.lookingNumber,
                                    model.lookingNumber
                                )
                            )
                            binding.activityLooking.show()
                        } else {
                            binding.activityLooking.gone()
                        }

                        if (model.appliedNumber == 0 && model.lookingNumber == 0) {
                            binding.activityContainer.gone()
                        } else {
                            binding.activityContainer.show()
                        }

                        if (!(model.appliedNumber == 0 || model.lookingNumber == 0)) {
                            binding.activityDivider.show()
                        } else {
                            binding.activityDivider.gone()
                        }

                        binding.container.show()
                        binding.containerControls.show()
                        binding.indicator.hide()
                    }


                    VacancyDetailsState.Loading -> {
                        binding.container.hide()
                        binding.containerControls.hide()
                        binding.indicator.show()
                    }
                }
            }
        }

        binding.recycler.apply {
            this.adapter = recyclerAdapter
            this.layoutManager = recyclerLayoutManager
            this.itemAnimator?.changeDuration = 0
            this.addItemDecoration(marginsDecorator)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()

        binding.recycler.apply {
            this.adapter = null
            this.layoutManager = null
        }
    }

    private fun applyButton() =
        ButtonItem.Big2(APPLY_BUTTON, getString(R.string.apply), onClick = { eventDispatcher ->
            id?.let { id ->
                eventDispatcher.dispatch(ApplyPressed(id))
            }
        })

    private fun formatSchedules(schedules: List<Schedule>): String {
        return schedules.joinToString(separator = ", ") {
            when (it) {
                Schedule.FULL_TIME -> getString(R.string.full_time)
                Schedule.FULL_DAY -> getString(R.string.full_day)
                Schedule.PART_TIME -> getString(R.string.part_time)
                Schedule.REMOTE -> getString(R.string.remote_work)
            }
        }
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
    }

    companion object {
        const val APPLY_BUTTON = "APPLY_BUTTON"
    }
}

