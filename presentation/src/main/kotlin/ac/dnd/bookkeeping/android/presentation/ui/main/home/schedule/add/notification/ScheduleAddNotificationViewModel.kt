package ac.dnd.bookkeeping.android.presentation.ui.main.home.schedule.add.notification

import ac.dnd.bookkeeping.android.presentation.common.base.BaseViewModel
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.asEventFlow
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

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
