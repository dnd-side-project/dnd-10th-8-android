package ac.dnd.bookkeeping.android.presentation.ui.main.home.schedule.add

import ac.dnd.bookkeeping.android.domain.model.error.ServerException
import ac.dnd.bookkeeping.android.domain.model.feature.schedule.AlarmRepeatType
import ac.dnd.bookkeeping.android.domain.usecase.feature.schedule.AddScheduleUseCase
import ac.dnd.bookkeeping.android.presentation.common.base.BaseViewModel
import ac.dnd.bookkeeping.android.presentation.common.base.ErrorEvent
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.asEventFlow
import ac.dnd.bookkeeping.android.presentation.model.schedule.ScheduleAlarmType
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.minus
import javax.inject.Inject

@HiltViewModel
class ScheduleAddViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val addScheduleUseCase: AddScheduleUseCase
) : BaseViewModel() {

    private val _state: MutableStateFlow<ScheduleAddState> = MutableStateFlow(ScheduleAddState.Init)
    val state: StateFlow<ScheduleAddState> = _state.asStateFlow()

    private val _event: MutableEventFlow<ScheduleAddEvent> = MutableEventFlow()
    val event: EventFlow<ScheduleAddEvent> = _event.asEventFlow()

    fun onIntent(intent: ScheduleAddIntent) {
        when (intent) {
            is ScheduleAddIntent.OnConfirm -> onConfirm(
                relationId = intent.relationId,
                day = intent.day,
                event = intent.event,
                repeatType = intent.repeatType,
                repeatFinish = intent.repeatFinish,
                alarm = intent.alarm,
                time = intent.time,
                link = intent.link,
                location = intent.location,
                memo = intent.memo
            )
        }
    }

    private fun onConfirm(
        relationId: Long,
        day: LocalDate,
        event: String,
        repeatType: AlarmRepeatType?,
        repeatFinish: LocalDate?,
        alarm: ScheduleAlarmType,
        time: LocalTime?,
        link: String,
        location: String,
        memo: String
    ) {
        launch {
            _state.value = ScheduleAddState.Loading
            val alarmDateTime: LocalDateTime? = when (alarm) {
                ScheduleAlarmType.None -> null
                ScheduleAlarmType.TodayNine -> LocalDateTime(day, LocalTime(9, 0))
                ScheduleAlarmType.TodayTwelve -> LocalDateTime(day, LocalTime(12, 0))
                ScheduleAlarmType.YesterdayNine -> LocalDateTime(
                    day.minus(1, DateTimeUnit.DAY),
                    LocalTime(9, 0)
                )

                ScheduleAlarmType.TwoDaysAgoNine -> LocalDateTime(
                    day.minus(2, DateTimeUnit.DAY),
                    LocalTime(9, 0)
                )

                ScheduleAlarmType.OneWeekAgoNine -> LocalDateTime(
                    day.minus(1, DateTimeUnit.WEEK),
                    LocalTime(9, 0)
                )
            }
            addScheduleUseCase(
                relationId = relationId,
                day = day,
                event = event,
                repeatType = repeatType,
                repeatFinish = repeatFinish,
                alarm = alarmDateTime,
                time = time,
                link = link,
                location = location,
                memo = memo
            ).onSuccess {
                _state.value = ScheduleAddState.Init
                _event.emit(ScheduleAddEvent.AddSchedule.Success)
            }.onFailure { exception ->
                _state.value = ScheduleAddState.Init
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
