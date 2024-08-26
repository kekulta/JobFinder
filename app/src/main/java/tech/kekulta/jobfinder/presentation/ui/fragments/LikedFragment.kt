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
import tech.kekulta.jobfinder.databinding.FragmentLikedBinding
import tech.kekulta.jobfinder.presentation.ui.events.EventDispatcher
import tech.kekulta.jobfinder.presentation.ui.events.UiEvent
import tech.kekulta.jobfinder.presentation.ui.events.UiEventDispatcher
import tech.kekulta.jobfinder.presentation.ui.events.interceptBackPressed
import tech.kekulta.jobfinder.presentation.ui.recycler.adapters.TitleAdapter
import tech.kekulta.jobfinder.presentation.ui.recycler.adapters.VacancyAdapter
import tech.kekulta.jobfinder.presentation.ui.recycler.base.buildCompositeAdapter
import tech.kekulta.jobfinder.presentation.ui.recycler.decorations.Margins
import tech.kekulta.jobfinder.presentation.ui.recycler.decorations.RecyclerViewMargins
import tech.kekulta.jobfinder.presentation.ui.recycler.items.TitleItem
import tech.kekulta.jobfinder.presentation.ui.recycler.items.VacancyItem
import tech.kekulta.jobfinder.presentation.viewmodels.LikedViewModel
import tech.kekulta.uikit.dimen
import tech.kekulta.uikit.handleSystemBar
import tech.kekulta.uikit.R as uikit

class LikedFragment : Fragment(R.layout.fragment_liked),
    EventDispatcher<UiEvent> by UiEventDispatcher() {
    private val binding by viewBinding(FragmentLikedBinding::bind)
    private val viewModel by viewModel<LikedViewModel>()

    private val marginsDecorator by lazy {
        RecyclerViewMargins(
            adapter = recyclerAdapter,
            margins = listOf(
                Margins(marginTop = requireContext().dimen(uikit.dimen.size_x16)),
                Margins(),
            ),
        )
    }

    private val recyclerAdapter by lazy {
        buildCompositeAdapter {
            add(VacancyAdapter().also { it.setParent(this@LikedFragment) })
            add(TitleAdapter())
        }
    }
    private val recyclerLayoutManager by lazy {
        LinearLayoutManager(
            requireContext(), RecyclerView.VERTICAL, false
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setParent(viewModel)
        interceptBackPressed(viewModel)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleSystemBar(binding.main)
        binding.recycler.apply {
            this.adapter = recyclerAdapter
            this.layoutManager = recyclerLayoutManager
            this.itemAnimator?.changeDuration = 0
            this.addItemDecoration(marginsDecorator)
        }

        lifecycleScope.launch {
            viewModel.observeLikes().collect { vacancies ->
                recyclerAdapter.submitList(buildList {
                    add(getTitle(vacancies.size))
                    addAll(vacancies.map { VacancyItem(it) })
                })
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.recycler.apply {
            this.adapter = null
            this.layoutManager = null
        }
    }

    private fun getTitle(count: Int): TitleItem {
        return TitleItem(
            title = resources.getString(R.string.liked_title),
            subtitle = resources.getQuantityString(R.plurals.vacancies_number, count, count)
        )
    }
}