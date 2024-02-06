package ac.dnd.bookkeeping.android.presentation.ui.main.home.schedule.add.repeat

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
class ScheduleAddRepeatViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    private val _state: MutableStateFlow<ScheduleAddRepeatState> =
        MutableStateFlow(ScheduleAddRepeatState.Init)
    val state: StateFlow<ScheduleAddRepeatState> = _state.asStateFlow()

    private val _event: MutableEventFlow<ScheduleAddRepeatEvent> = MutableEventFlow()
    val event: EventFlow<ScheduleAddRepeatEvent> = _event.asEventFlow()

    fun onIntent(intent: ScheduleAddRepeatIntent) {

    }
}
