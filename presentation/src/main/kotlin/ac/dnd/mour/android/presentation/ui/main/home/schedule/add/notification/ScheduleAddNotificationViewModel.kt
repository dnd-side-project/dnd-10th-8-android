package ac.dnd.mour.android.presentation.ui.main.home.schedule.add.notification

import ac.dnd.mour.android.presentation.common.base.BaseViewModel
import ac.dnd.mour.android.common.coroutine.event.EventFlow
import ac.dnd.mour.android.common.coroutine.event.MutableEventFlow
import ac.dnd.mour.android.common.coroutine.event.asEventFlow
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class ScheduleAddNotificationViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    private val _state: MutableStateFlow<ScheduleAddNotificationState> =
        MutableStateFlow(ScheduleAddNotificationState.Init)
    val state: StateFlow<ScheduleAddNotificationState> = _state.asStateFlow()

    private val _event: MutableEventFlow<ScheduleAddNotificationEvent> = MutableEventFlow()
    val event: EventFlow<ScheduleAddNotificationEvent> = _event.asEventFlow()

    fun onIntent(intent: ScheduleAddNotificationIntent) {

    }
}
