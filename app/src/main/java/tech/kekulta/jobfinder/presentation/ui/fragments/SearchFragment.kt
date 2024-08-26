package tech.kekulta.jobfinder.presentation.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import tech.kekulta.jobfinder.R
import tech.kekulta.jobfinder.databinding.FragmentSearchBinding
import tech.kekulta.jobfinder.presentation.ui.events.ShowMore
import tech.kekulta.jobfinder.presentation.ui.events.interceptBackPressed
import tech.kekulta.jobfinder.presentation.ui.recycler.adapters.ButtonBig1Adapter
import tech.kekulta.jobfinder.presentation.ui.recycler.adapters.OfferBlockAdapter
import tech.kekulta.jobfinder.presentation.ui.recycler.adapters.SearchBlockAdapter
import tech.kekulta.jobfinder.presentation.ui.recycler.adapters.TitleAdapter
import tech.kekulta.jobfinder.presentation.ui.recycler.adapters.VacancyAdapter
import tech.kekulta.jobfinder.presentation.ui.recycler.base.buildCompositeAdapter
import tech.kekulta.jobfinder.presentation.ui.recycler.decorations.Margins
import tech.kekulta.jobfinder.presentation.ui.recycler.decorations.RecyclerViewMargins
import tech.kekulta.jobfinder.presentation.ui.recycler.items.ButtonItem
import tech.kekulta.jobfinder.presentation.ui.recycler.items.OfferBlockItem
import tech.kekulta.jobfinder.presentation.ui.recycler.items.SearchBlockItem
import tech.kekulta.jobfinder.presentation.ui.recycler.items.TitleItem
import tech.kekulta.jobfinder.presentation.ui.recycler.items.VacancyItem
import tech.kekulta.jobfinder.presentation.viewmodels.search.SearchState
import tech.kekulta.jobfinder.presentation.viewmodels.search.SearchViewModel
import tech.kekulta.uikit.dimen
import tech.kekulta.uikit.handleSystemBar
import tech.kekulta.uikit.R as uikit

class SearchFragment : Fragment(R.layout.fragment_search) {
    private val binding by viewBinding(FragmentSearchBinding::bind)
    private val viewModel by viewModel<SearchViewModel>()

    private val marginsDecorator by lazy {
        RecyclerViewMargins(
            adapter = recyclerAdapter,
            margins = listOf(
                Margins(),
                Margins(marginTop = requireContext().dimen(uikit.dimen.size_x16)),
                Margins(marginTop = requireContext().dimen(uikit.dimen.size_x16)),
                Margins(marginTop = requireContext().dimen(uikit.dimen.size_x16)),
                Margins(marginTop = requireContext().dimen(uikit.dimen.size_x16)),
            ),
        )
    }

    private val recyclerAdapter by lazy {
        buildCompositeAdapter {
            add(SearchBlockAdapter().also { it.setParent(viewModel) })
            add(VacancyAdapter().also { it.setParent(viewModel) })
            add(OfferBlockAdapter().also { it.setParent(viewModel) })
            add(ButtonBig1Adapter().also { it.setParent(viewModel) })
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
        interceptBackPressed(viewModel)
        viewModel.update()
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

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.observeState().collect { state ->
                    when (state) {
                        is SearchState.Recommendations -> {
                            buildList {
                                add(
                                    SearchBlockItem.Idle(
                                        SEARCH_ID,
                                        getString(R.string.recommendtions_search_hint)
                                    )
                                )
                                add(OfferBlockItem(OFFERS_ID, state.offers))
                                add(TitleItem(title = getString(R.string.vacancies_for_you)))
                                addAll(state.vacancies.map { VacancyItem(it) })
                                if (state.vacanciesCount != 0 && state.vacanciesCount - state.vacancies.size > 0) {
                                    add(ButtonItem.Big1(
                                        BUTTON_ID, resources.getQuantityString(
                                            R.plurals.vacancies_more_count,
                                            state.vacanciesCount - state.vacancies.size,
                                            state.vacanciesCount - state.vacancies.size,
                                        )
                                    ) {
                                        it.dispatch(ShowMore)
                                    })
                                }
                            }.let { recyclerAdapter.submitList(it) }

                            binding.map.hide()
                        }

                        is SearchState.Search -> {
                            buildList {
                                add(
                                    SearchBlockItem.Search(
                                        SEARCH_ID, state.search ?: "", state.vacancies.size
                                    )
                                )
                                addAll(state.vacancies.map { VacancyItem(it) })
                            }.let { recyclerAdapter.submitList(it) }

                            binding.map.show()
                        }
                    }
                }
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

    companion object {
        const val OFFERS_ID = "OFFERS_ID"
        const val BUTTON_ID = "BUTTON_ID"
        const val SEARCH_ID = "SEARCH_ID"
    }
}