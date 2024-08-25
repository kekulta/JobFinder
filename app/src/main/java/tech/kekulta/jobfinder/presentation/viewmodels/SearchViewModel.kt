package tech.kekulta.jobfinder.presentation.viewmodels

import androidx.core.os.bundleOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import tech.kekulta.jobfinder.domain.repositories.LikesRepository
import tech.kekulta.jobfinder.domain.repositories.OffersRepository
import tech.kekulta.jobfinder.domain.repositories.VacanciesRepository
import tech.kekulta.jobfinder.domain.usescases.GetRecommendationsUseCase
import tech.kekulta.jobfinder.presentation.ui.events.BackPressed
import tech.kekulta.jobfinder.presentation.ui.events.DislikeVacancyPressed
import tech.kekulta.jobfinder.presentation.ui.events.EventDispatcher
import tech.kekulta.jobfinder.presentation.ui.events.LikeVacancyPressed
import tech.kekulta.jobfinder.presentation.ui.events.LinkPressed
import tech.kekulta.jobfinder.presentation.ui.events.NavEventDispatcher
import tech.kekulta.jobfinder.presentation.ui.events.NavigateBack
import tech.kekulta.jobfinder.presentation.ui.events.NavigateTo
import tech.kekulta.jobfinder.presentation.ui.events.OpenDialog
import tech.kekulta.jobfinder.presentation.ui.events.OpenLink
import tech.kekulta.jobfinder.presentation.ui.events.ResponseToVacancyPressed
import tech.kekulta.jobfinder.presentation.ui.events.ShowMore
import tech.kekulta.jobfinder.presentation.ui.events.UiEvent
import tech.kekulta.jobfinder.presentation.ui.events.UiEventDispatcher
import tech.kekulta.jobfinder.presentation.ui.events.VacancyPressed
import tech.kekulta.navigation.DestArgs
import tech.kekulta.navigation.Destination

class SearchViewModel(
    private val getRecommendationsUseCase: GetRecommendationsUseCase,
    private val vacanciesRepository: VacanciesRepository,
    private val offersRepository: OffersRepository,
    private val likesRepository: LikesRepository,
    private val navEventDispatcher: NavEventDispatcher,
) : AbstractCoroutineViewModel(), EventDispatcher<UiEvent> by UiEventDispatcher() {
    private val screenType = MutableStateFlow(SearchStateType.RECOMMENDATIONS)
    private val search = MutableStateFlow<String?>(null)
    private val screenState =
        MutableStateFlow<SearchState>(SearchState.Recommendations(emptyList(), 0, emptyList()))

    init {
        setHandle {
            when (it) {
                is BackPressed -> {
                    if (screenType.value == SearchStateType.SEARCH) {
                        screenType.value = SearchStateType.RECOMMENDATIONS
                    } else {
                        navEventDispatcher.dispatch(NavigateBack)
                    }
                    true
                }

                is LinkPressed -> {
                    navEventDispatcher.dispatch(OpenLink(it.link))
                    true
                }

                is VacancyPressed -> {
                    navEventDispatcher.dispatch(
                        NavigateTo(
                            Destination.VACANCY_DETAILS,
                            bundleOf(DestArgs.VACANCY_ID to it.id.id),
                        )
                    )
                    true
                }

                is ResponseToVacancyPressed -> {
                    navEventDispatcher.dispatch(
                        OpenDialog(
                            Destination.VACANCY_RESPONSE,
                            bundleOf(DestArgs.VACANCY_ID to it.id.id)
                        )
                    )

                    true
                }

                is LikeVacancyPressed -> {
                    likesRepository.likeVacancy(it.id)
                    true
                }

                is DislikeVacancyPressed -> {
                    likesRepository.dislikeVacancy(it.id)
                    true
                }

                is ShowMore -> {
                    screenType.update { SearchStateType.SEARCH }
                    true
                }

                else -> false
            }
        }

        launchScope {
            combine(
                getRecommendationsUseCase.execute(),
                offersRepository.observeRecommendation(),
                search,
                screenType
            ) { vacs, offers, search, type ->
                screenState.update {
                    when (type) {
                        SearchStateType.RECOMMENDATIONS -> {
                            SearchState.Recommendations(offers, vacs.size, vacs.take(3))
                        }

                        SearchStateType.SEARCH -> {
                            SearchState.Search(search ?: "Вакансии для вас", vacs.size, vacs)
                        }
                    }
                }
            }.collect {}
        }
    }

    fun observeState(): Flow<SearchState> = screenState

    fun update() {
        vacanciesRepository.fetch()
        offersRepository.fetch()
    }
}

