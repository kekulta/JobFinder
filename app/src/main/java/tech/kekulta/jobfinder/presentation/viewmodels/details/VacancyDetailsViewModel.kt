package tech.kekulta.jobfinder.presentation.viewmodels.details

import androidx.core.os.bundleOf
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import tech.kekulta.jobfinder.domain.models.VacancyId
import tech.kekulta.jobfinder.domain.repositories.LikesRepository
import tech.kekulta.jobfinder.domain.usescases.ObserveByIdUseCase
import tech.kekulta.jobfinder.presentation.ui.events.BackPressed
import tech.kekulta.jobfinder.presentation.ui.events.DislikeVacancyPressed
import tech.kekulta.jobfinder.presentation.ui.events.EventDispatcher
import tech.kekulta.jobfinder.presentation.ui.events.LikeVacancyPressed
import tech.kekulta.jobfinder.presentation.ui.events.NavEventDispatcher
import tech.kekulta.jobfinder.presentation.ui.events.NavigateBack
import tech.kekulta.jobfinder.presentation.ui.events.OpenDialog
import tech.kekulta.jobfinder.presentation.ui.events.ApplyPressed
import tech.kekulta.jobfinder.presentation.ui.events.UiEvent
import tech.kekulta.jobfinder.presentation.ui.events.UiEventDispatcher
import tech.kekulta.jobfinder.presentation.viewmodels.base.AbstractCoroutineViewModel
import tech.kekulta.navigation.DestArgs
import tech.kekulta.navigation.Destination

@OptIn(ExperimentalCoroutinesApi::class)
class VacancyDetailsViewModel(
    private val navEventDispatcher: NavEventDispatcher,
    private val likesRepository: LikesRepository,
    private val observeByIdUseCase: ObserveByIdUseCase,
) : AbstractCoroutineViewModel(), EventDispatcher<UiEvent> by UiEventDispatcher() {
    private val idState = MutableStateFlow<VacancyId?>(null)
    private val state = MutableStateFlow<VacancyDetailsState>(VacancyDetailsState.Loading)

    init {
        setHandle { event ->
            when (event) {
                BackPressed -> {
                    navEventDispatcher.dispatch(NavigateBack)
                    true
                }

                is ApplyPressed -> {
                    navEventDispatcher.dispatch(
                        OpenDialog(
                            Destination.APPLICATION_DIALOG,
                            bundleOf(DestArgs.VACANCY_ID to event.id.id)
                        )
                    )

                    true
                }

                is LikeVacancyPressed -> {
                    likesRepository.likeVacancy(event.id)
                    true
                }

                is DislikeVacancyPressed -> {
                    likesRepository.dislikeVacancy(event.id)
                    true
                }

                else -> false
            }
        }

        launchScope {
            idState.flatMapLatest { id ->
                id?.let { id ->
                    observeByIdUseCase.execute(listOf(id)).map { vacancies ->
                        vacancies.firstOrNull()?.let {
                            VacancyDetailsState.Loaded(it)
                        } ?: VacancyDetailsState.Loading
                    }
                } ?: flow { emit(VacancyDetailsState.Loading) }
            }.collect { newState ->
                state.update { newState }
            }
        }
    }

    fun observeState(): Flow<VacancyDetailsState> = state
    fun setId(id: VacancyId) {
        state.value = VacancyDetailsState.Loading
        idState.value = id
    }
}