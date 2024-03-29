package ac.dnd.mour.android.presentation.ui.main.home.history.unrecorded

import ac.dnd.mour.android.domain.model.error.ServerException
import ac.dnd.mour.android.domain.model.feature.schedule.UnrecordedSchedule
import ac.dnd.mour.android.domain.usecase.feature.heart.AddUnrecordedHeartUseCase
import ac.dnd.mour.android.domain.usecase.feature.schedule.GetUnrecordedScheduleListUseCase
import ac.dnd.mour.android.presentation.common.base.BaseViewModel
import ac.dnd.mour.android.presentation.common.base.ErrorEvent
import ac.dnd.mour.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.mour.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.mour.android.presentation.common.util.coroutine.event.asEventFlow
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class HistoryUnrecordedViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val addUnrecordedHeartUseCase: AddUnrecordedHeartUseCase,
    private val getUnrecordedScheduleListUseCase: GetUnrecordedScheduleListUseCase
) : BaseViewModel() {

    private val _state: MutableStateFlow<HistoryUnrecordedState> =
        MutableStateFlow(HistoryUnrecordedState.Init)
    val state: StateFlow<HistoryUnrecordedState> = _state.asStateFlow()

    private val _event: MutableEventFlow<HistoryUnrecordedEvent> = MutableEventFlow()
    val event: EventFlow<HistoryUnrecordedEvent> = _event.asEventFlow()

    private val _unrecordedList: MutableStateFlow<List<UnrecordedSchedule>> =
        MutableStateFlow(emptyList())
    val unrecordedList: StateFlow<List<UnrecordedSchedule>> = _unrecordedList.asStateFlow()

    init {
        loadUnrecordedHeartList()
    }

    fun onIntent(intent: HistoryUnrecordedIntent) {
        when (intent) {
            is HistoryUnrecordedIntent.OnRecord -> {
                saveUnrecordedHeart(
                    scheduleId = intent.scheduleId,
                    money = intent.money,
                    tags = intent.tags
                )
            }
            //TODO -> skip api
            is HistoryUnrecordedIntent.OnSkip -> skipRecord(intent.scheduleId)
        }
    }

    private fun loadUnrecordedHeartList(

    ) {
        launch {
            _state.value = HistoryUnrecordedState.Loading
            getUnrecordedScheduleListUseCase("")
                .onSuccess {
                    _state.value = HistoryUnrecordedState.Init
                    _unrecordedList.value = it
                }
                .onFailure { exception ->
                    _state.value = HistoryUnrecordedState.Init
                    when (exception) {
                        is ServerException -> {
                            _errorEvent.emit(ErrorEvent.InvalidRequest(exception))
                        }

                        else -> {
                            _errorEvent.emit(ErrorEvent.UnavailableServer(exception))
                        }
                    }
                }
        }
    }

    private fun saveUnrecordedHeart(
        scheduleId: Long,
        money: Long,
        tags: List<String>
    ) {
        launch {
            _state.value = HistoryUnrecordedState.Loading
            addUnrecordedHeartUseCase(
                scheduleId = scheduleId,
                money = money,
                tags = tags
            )
                .onSuccess {
                    _state.value = HistoryUnrecordedState.Init
                    _event.emit(HistoryUnrecordedEvent.ShowNextData)
                }
                .onFailure { exception ->
                    _state.value = HistoryUnrecordedState.Init
                    when (exception) {
                        is ServerException -> {
                            _errorEvent.emit(ErrorEvent.InvalidRequest(exception))
                        }

                        else -> {
                            _errorEvent.emit(ErrorEvent.UnavailableServer(exception))
                        }
                    }
                }
        }
    }

    private fun skipRecord(id: Long) {
        launch {
            _event.emit(HistoryUnrecordedEvent.ShowNextData)
        }
    }
}
