package ac.dnd.bookkeeping.android.presentation.ui.main.registration.main

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
class RegistrationMainViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    private val _state: MutableStateFlow<RegistrationMainState> =
        MutableStateFlow(RegistrationMainState.Init)
    val state: StateFlow<RegistrationMainState> = _state.asStateFlow()

    private val _event: MutableEventFlow<RegistrationMainEvent> = MutableEventFlow()
    val event: EventFlow<RegistrationMainEvent> = _event.asEventFlow()

    fun onIntent(intent: RegistrationMainIntent) {
        when (intent) {
            RegistrationMainIntent.OnCheckUserName -> checkUserNameValid()
            RegistrationMainIntent.OnClickSubmit -> goToNextStep()
        }
    }

    private fun checkUserNameValid() {
        launch {

        }
    }

    private fun goToNextStep() {
        launch {

        }
    }
}
