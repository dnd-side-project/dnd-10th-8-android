package ac.dnd.bookkeeping.android.presentation.ui.main.home.history.registration

import ac.dnd.bookkeeping.android.presentation.common.base.BaseViewModel
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.asEventFlow
import ac.dnd.bookkeeping.android.presentation.ui.main.home.history.main.HistoryMainEvent
import ac.dnd.bookkeeping.android.presentation.ui.main.home.history.main.HistoryMainState
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HistoryRegistrationViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
): BaseViewModel() {

    private val _state: MutableStateFlow<HistoryRegistrationState> =
        MutableStateFlow(HistoryRegistrationState.Init)
    val state: StateFlow<HistoryRegistrationState> = _state.asStateFlow()

    private val _event: MutableEventFlow<HistoryRegistrationEvent> = MutableEventFlow()
    val event: EventFlow<HistoryRegistrationEvent> = _event.asEventFlow()

    fun onIntent(intent: HistoryRegistrationIntent){

    }
}
