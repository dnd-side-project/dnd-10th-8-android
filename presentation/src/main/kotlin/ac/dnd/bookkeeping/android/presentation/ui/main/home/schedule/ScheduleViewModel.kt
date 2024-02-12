package ac.dnd.bookkeeping.android.presentation.ui.main.home.schedule


import ac.dnd.bookkeeping.android.domain.model.error.ServerException
import ac.dnd.bookkeeping.android.domain.model.feature.schedule.Schedule
import ac.dnd.bookkeeping.android.domain.usecase.feature.schedule.GetScheduleListUseCase
import ac.dnd.bookkeeping.android.presentation.common.base.BaseViewModel
import ac.dnd.bookkeeping.android.presentation.common.base.ErrorEvent
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.asEventFlow
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.todayIn
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getScheduleListUseCase: GetScheduleListUseCase
) : BaseViewModel() {

    private val _state: MutableStateFlow<ScheduleState> = MutableStateFlow(ScheduleState.Init)
    val state: StateFlow<ScheduleState> = _state.asStateFlow()

    private val _event: MutableEventFlow<ScheduleEvent> = MutableEventFlow()
    val event: EventFlow<ScheduleEvent> = _event.asEventFlow()

    private val _scheduleList: MutableStateFlow<List<Schedule>> = MutableStateFlow(emptyList())
    val scheduleList: StateFlow<List<Schedule>> = _scheduleList.asStateFlow()

    private val now = Clock.System.todayIn(TimeZone.currentSystemDefault())

    init {
        launch {
            refresh(
                year = now.year,
                month = now.month.number
            )
        }
    }

    fun onIntent(intent: ScheduleIntent) {
        when (intent) {
            is ScheduleIntent.ChangeDate -> {
                changeDate(intent.date)
            }
        }
    }

    private fun changeDate(
        date: LocalDate
    ) {
        launch {
            refresh(
                year = date.year,
                month = date.month.number
            )
        }
    }

    private fun refresh(
        year: Int,
        month: Int
    ) {
        launch {
            _state.value = ScheduleState.Loading
            getScheduleListUseCase(
                year = year,
                month = month
            ).onSuccess {
                _state.value = ScheduleState.Init
                _scheduleList.value = it
            }.onFailure { exception ->
                _state.value = ScheduleState.Init
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
}
