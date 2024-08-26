package tech.kekulta.jobfinder.presentation.viewmodels.application

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import tech.kekulta.jobfinder.domain.models.VacancyId
import tech.kekulta.jobfinder.domain.repositories.VacanciesRepository
import tech.kekulta.jobfinder.presentation.ui.events.AddCover
import tech.kekulta.jobfinder.presentation.ui.events.ApplyPressed
import tech.kekulta.jobfinder.presentation.ui.events.BackPressed
import tech.kekulta.jobfinder.presentation.ui.events.EventDispatcher
import tech.kekulta.jobfinder.presentation.ui.events.UiEvent
import tech.kekulta.jobfinder.presentation.ui.events.UiEventDispatcher
import tech.kekulta.jobfinder.presentation.viewmodels.base.AbstractCoroutineViewModel

@OptIn(ExperimentalCoroutinesApi::class)
class ApplicationViewModel(
    private val vacanciesRepository: VacanciesRepository,
) : AbstractCoroutineViewModel(), EventDispatcher<UiEvent> by UiEventDispatcher() {
    private val channel = Channel<ApplicationEvent>()
    private val idState = MutableStateFlow<VacancyId?>(null)
    private val coverState = MutableStateFlow(false)
    private val screenState = MutableStateFlow<ApplicationState>(ApplicationState.Loading)
    private var cover: String = ""

    init {
        setHandle {
            when (it) {
                is BackPressed -> {
                    when (val s = screenState.value) {
                        is ApplicationState.Loading, is ApplicationState.NoCover -> {
                            viewModelScope.launch {
                                channel.send(ApplicationEvent.CloseDialog)
                            }
                        }

                        is ApplicationState.OpenCover -> {
                            screenState.value = ApplicationState.NoCover(title = s.title)
                        }
                    }
                    true
                }

                is ApplyPressed -> {
                    viewModelScope.launch {
                        channel.send(ApplicationEvent.CloseDialog)
                    }
                    true
                }

                is AddCover -> {
                    val s = screenState.value
                    if (s is ApplicationState.NoCover) {
                        screenState.value = ApplicationState.OpenCover(title = s.title)
                    }

                    true
                }

                else -> false
            }
        }

        launchScope {
            combine(idState, coverState) { id, cover -> id to cover }.flatMapLatest { (id, cover) ->
                id?.let { id ->
                    vacanciesRepository.observeByIds(ids = listOf(id)).map {
                        it.firstOrNull()?.let { vacancy ->
                            if (cover) {
                                ApplicationState.OpenCover(vacancy.title)
                            } else {
                                ApplicationState.NoCover(vacancy.title)
                            }
                        } ?: ApplicationState.Loading
                    }
                } ?: emptyFlow()
            }.collect { state ->
                screenState.value = state
            }
        }
    }

    fun observeState(): Flow<ApplicationState> = screenState

    fun observeEvents(): Flow<ApplicationEvent> = channel.consumeAsFlow()

    fun setId(id: VacancyId) {
        cover = ""
        idState.value = id
    }
}

