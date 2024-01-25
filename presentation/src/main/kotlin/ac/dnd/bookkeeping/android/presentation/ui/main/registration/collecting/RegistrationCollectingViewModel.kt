package ac.dnd.bookkeeping.android.presentation.ui.main.registration.collecting

import ac.dnd.bookkeeping.android.presentation.common.base.BaseViewModel
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.asEventFlow
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationCollectingViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    private val _state: MutableStateFlow<RegistrationCollectingState> =
        MutableStateFlow(RegistrationCollectingState.Init)
    val state: StateFlow<RegistrationCollectingState> = _state.asStateFlow()

    private val _event: MutableEventFlow<RegistrationCollectingEvent> = MutableEventFlow()
    val event: EventFlow<RegistrationCollectingEvent> = _event.asEventFlow()

    fun onIntent(intent: RegistrationCollectingIntent) {
        when (intent) {
            RegistrationCollectingIntent.SubmitUserInput -> {
                viewModelScope.launch {
                    _event.emit(RegistrationCollectingEvent.OnClickSubmit)
                }
            }
        }
    }
}
