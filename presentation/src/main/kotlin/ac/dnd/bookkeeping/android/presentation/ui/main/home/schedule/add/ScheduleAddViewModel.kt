package ac.dnd.bookkeeping.android.presentation.ui.main.home.schedule.add

import ac.dnd.bookkeeping.android.domain.usecase.feature.schedule.AddScheduleUseCase
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
            ScheduleAddIntent.OnConfirm -> onConfirm()
        }
    }

    private fun onConfirm() {

    }
}
