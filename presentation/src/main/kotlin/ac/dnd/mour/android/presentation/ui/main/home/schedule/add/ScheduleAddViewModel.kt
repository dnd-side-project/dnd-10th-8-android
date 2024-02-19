package ac.dnd.mour.android.presentation.ui.main.home.schedule.add

import ac.dnd.mour.android.domain.model.error.ServerException
import ac.dnd.mour.android.domain.usecase.feature.schedule.AddScheduleUseCase
import ac.dnd.mour.android.domain.usecase.feature.schedule.DeleteScheduleUseCase
import ac.dnd.mour.android.domain.usecase.feature.schedule.EditScheduleUseCase
import ac.dnd.mour.android.domain.usecase.feature.schedule.GetScheduleUseCase
import ac.dnd.mour.android.presentation.common.base.BaseViewModel
import ac.dnd.mour.android.presentation.common.base.ErrorEvent
import ac.dnd.mour.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.mour.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.mour.android.presentation.common.util.coroutine.event.asEventFlow
import ac.dnd.mour.android.presentation.model.schedule.ScheduleAlarmType
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
    private val addScheduleUseCase: AddScheduleUseCase,
    private val editScheduleUseCase: EditScheduleUseCase,
    private val getScheduleUseCase: GetScheduleUseCase,
    private val deleteScheduleUseCase: DeleteScheduleUseCase
) : BaseViewModel() {

    private val _state: MutableStateFlow<ScheduleAddState> = MutableStateFlow(ScheduleAddState.Init)
    val state: StateFlow<ScheduleAddState> = _state.asStateFlow()

    private val _event: MutableEventFlow<ScheduleAddEvent> = MutableEventFlow()
    val event: EventFlow<ScheduleAddEvent> = _event.asEventFlow()

    val scheduleId: Long by lazy {
        savedStateHandle.get<Long>(ScheduleAddConstant.ROUTE_ARGUMENT_SCHEDULE_ID) ?: -1L
    }

    val isEdit: Boolean by lazy {
        scheduleId != -1L
    }

    init {
        if (scheduleId != -1L) {
            loadSchedule()
        }
    }

    private fun loadSchedule() {
        launch {
            _state.value = ScheduleAddState.Loading
            getScheduleUseCase(
                id = scheduleId
            ).onSuccess { schedule ->
                _state.value = ScheduleAddState.Init
                _event.emit(ScheduleAddEvent.LoadSchedule.Success(schedule))
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

    fun onIntent(intent: ScheduleAddIntent) {
        when (intent) {
            is ScheduleAddIntent.OnConfirm -> onConfirm(
                relationId = intent.relationId,
                day = intent.day,
                event = intent.event,
                alarm = intent.alarm,
                time = intent.time,
                link = intent.link,
                location = intent.location,
                memo = intent.memo
            )

            ScheduleAddIntent.OnRemove -> onRemove()
        }
    }

    private fun onConfirm(
        relationId: Long,
        day: LocalDate,
        event: String,
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

            if (scheduleId == -1L) {
                editScheduleUseCase(
                    id = scheduleId,
                    day = day,
                    event = event,
                    repeatType = null,
                    repeatFinish = null,
                    alarm = alarmDateTime,
                    time = time,
                    link = link,
                    location = location,
                    memo = memo
                ).onSuccess {
                    _state.value = ScheduleAddState.Init
                    _event.emit(ScheduleAddEvent.EditSchedule.Success)
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
            } else {
                addScheduleUseCase(
                    relationId = relationId,
                    day = day,
                    event = event,
                    repeatType = null,
                    repeatFinish = null,
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

    private fun onRemove() {
        launch {
            _state.value = ScheduleAddState.Loading
            deleteScheduleUseCase(
                id = scheduleId
            ).onSuccess {
                _state.value = ScheduleAddState.Init
                _event.emit(ScheduleAddEvent.RemoveSchedule.Success)
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
