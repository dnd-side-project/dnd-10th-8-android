package ac.dnd.bookkeeping.android.presentation.ui.main.registration.naming

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
class RegistrationNamingViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    private val _state: MutableStateFlow<RegistrationNamingState> =
        MutableStateFlow(RegistrationNamingState.Init)
    val state: StateFlow<RegistrationNamingState> = _state.asStateFlow()

    private val _event: MutableEventFlow<RegistrationNamingEvent> = MutableEventFlow()
    val event: EventFlow<RegistrationNamingEvent> = _event.asEventFlow()

    fun onIntent(intent: RegistrationNamingIntent) {
        when (intent) {
            RegistrationNamingIntent.OnClickSubmit -> goToNextStep()

        }
    }

    private fun goToNextStep() {
        launch {
            _event.emit(RegistrationNamingEvent.GoToNextStep)
        }
    }
}
