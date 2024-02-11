package ac.dnd.bookkeeping.android.presentation.ui.main.home.history

import ac.dnd.bookkeeping.android.domain.model.error.ServerException
import ac.dnd.bookkeeping.android.domain.model.feature.group.GroupWithRelationDetail
import ac.dnd.bookkeeping.android.domain.model.feature.schedule.UnrecordedSchedule
import ac.dnd.bookkeeping.android.domain.usecase.feature.group.GetGroupHeartHistoryUseCase
import ac.dnd.bookkeeping.android.domain.usecase.feature.schedule.GetUnrecordedScheduleListUseCase
import ac.dnd.bookkeeping.android.presentation.common.base.BaseViewModel
import ac.dnd.bookkeeping.android.presentation.common.base.ErrorEvent
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.asEventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.zip
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getGroupHeartHistoryUseCase: GetGroupHeartHistoryUseCase,
    private val getUnrecordedScheduleListUseCase: GetUnrecordedScheduleListUseCase
) : BaseViewModel() {

    private val _state: MutableStateFlow<HistoryState> = MutableStateFlow(HistoryState.Init)
    val state: StateFlow<HistoryState> = _state.asStateFlow()

    private val _event: MutableEventFlow<HistoryEvent> = MutableEventFlow()
    val event: EventFlow<HistoryEvent> = _event.asEventFlow()

    private val _unrecordedSchedule: MutableStateFlow<List<UnrecordedSchedule>> =
        MutableStateFlow(emptyList())
    val unrecordedSchedule: StateFlow<List<UnrecordedSchedule>> = _unrecordedSchedule.asStateFlow()

    private val _groups: MutableStateFlow<List<GroupWithRelationDetail>> =
        MutableStateFlow(emptyList())
    val groups: StateFlow<List<GroupWithRelationDetail>> = _groups.asStateFlow()

    init {
        launch {
            _state.value = HistoryState.Loading
            zip(
                { getGroupHeartHistoryUseCase() },
                { getUnrecordedScheduleListUseCase("") } //TODO name param ?
            ).onSuccess { (groups, unrecordedSchedule) ->
                _state.value = HistoryState.Init
                _groups.value = groups
                _unrecordedSchedule.value = unrecordedSchedule
            }.onFailure { exception ->
                _state.value = HistoryState.Init
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

    fun onIntent(intent: HistoryIntent) {

    }
}
