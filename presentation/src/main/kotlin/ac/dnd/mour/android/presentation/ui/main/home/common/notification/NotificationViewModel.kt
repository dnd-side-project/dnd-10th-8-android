package ac.dnd.mour.android.presentation.ui.main.home.common.notification

import ac.dnd.mour.android.domain.model.error.ServerException
import ac.dnd.mour.android.domain.model.feature.schedule.Alarm
import ac.dnd.mour.android.domain.usecase.feature.schedule.GetAlarmListUseCase
import ac.dnd.mour.android.presentation.common.base.BaseViewModel
import ac.dnd.mour.android.presentation.common.base.ErrorEvent
import ac.dnd.mour.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.mour.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.mour.android.presentation.common.util.coroutine.event.asEventFlow
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getAlarmListUseCase: GetAlarmListUseCase
) : BaseViewModel() {

    private val _state: MutableStateFlow<NotificationState> =
        MutableStateFlow(NotificationState.Init)
    val state: StateFlow<NotificationState> = _state.asStateFlow()

    private val _event: MutableEventFlow<NotificationEvent> = MutableEventFlow()
    val event: EventFlow<NotificationEvent> = _event.asEventFlow()

    private val _alarmList: MutableStateFlow<List<Alarm>> = MutableStateFlow(emptyList())
    val alarmList: StateFlow<List<Alarm>> = _alarmList.asStateFlow()

    init {
        launch {
            getAlarmListUseCase()
                .onSuccess {
                    _alarmList.value = it
                }.onFailure { exception ->
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

    fun onIntent(intent: NotificationIntent) {

    }
}
