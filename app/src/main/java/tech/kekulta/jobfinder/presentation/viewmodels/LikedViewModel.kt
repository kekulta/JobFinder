package tech.kekulta.jobfinder.presentation.viewmodels

import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import tech.kekulta.jobfinder.domain.models.VacancyModel
import tech.kekulta.jobfinder.domain.repositories.LikesRepository
import tech.kekulta.jobfinder.domain.usescases.GetLikedUseCase
import tech.kekulta.jobfinder.presentation.ui.events.BackPressed
import tech.kekulta.jobfinder.presentation.ui.events.DislikeVacancyPressed
import tech.kekulta.jobfinder.presentation.ui.events.EventDispatcher
import tech.kekulta.jobfinder.presentation.ui.events.LikeVacancyPressed
import tech.kekulta.jobfinder.presentation.ui.events.NavEventDispatcher
import tech.kekulta.jobfinder.presentation.ui.events.NavigateBack
import tech.kekulta.jobfinder.presentation.ui.events.NavigateTo
import tech.kekulta.jobfinder.presentation.ui.events.OpenDialog
import tech.kekulta.jobfinder.presentation.ui.events.ApplyPressed
import tech.kekulta.jobfinder.presentation.ui.events.UiEvent
import tech.kekulta.jobfinder.presentation.ui.events.UiEventDispatcher
import tech.kekulta.jobfinder.presentation.ui.events.VacancyPressed
import tech.kekulta.navigation.DestArgs
import tech.kekulta.navigation.Destination

class LikedViewModel(
    private val likesRepository: LikesRepository,
    private val getLikedUseCase: GetLikedUseCase,
    private val navEventDispatcher: NavEventDispatcher,
) : ViewModel(), EventDispatcher<UiEvent> by UiEventDispatcher() {

    init {
        setHandle {
            when (it) {
                is BackPressed -> {
                    navEventDispatcher.dispatch(NavigateBack)
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

                is ApplyPressed -> {
                    navEventDispatcher.dispatch(
                        OpenDialog(
                            Destination.APPLICATION_DIALOG,
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

                else -> false
            }
        }
    }

    fun observeLikes(): Flow<List<VacancyModel>> {
        return getLikedUseCase.execute()
    }
}