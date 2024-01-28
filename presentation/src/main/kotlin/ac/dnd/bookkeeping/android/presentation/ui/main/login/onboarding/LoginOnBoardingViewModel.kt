package ac.dnd.bookkeeping.android.presentation.ui.main.login.onboarding

import ac.dnd.bookkeeping.android.presentation.common.base.BaseViewModel
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.asEventFlow
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginOnBoardingViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    private val _loadingState: MutableStateFlow<LoginOnBoardingState.Loading> =
        MutableStateFlow(LoginOnBoardingState.Loading.Progress)
    val loadingState: StateFlow<LoginOnBoardingState.Loading> = _loadingState.asStateFlow()

    private val _buttonState: MutableStateFlow<LoginOnBoardingState.Button> =
        MutableStateFlow(LoginOnBoardingState.Button.Default)
    val buttonState: StateFlow<LoginOnBoardingState.Button> = _buttonState.asStateFlow()

    private val _event: MutableEventFlow<LoginOnBoardingEvent> = MutableEventFlow()
    val event: EventFlow<LoginOnBoardingEvent> = _event.asEventFlow()

    fun onIntent(intent: LoginOnBoardingIntent) {
        when (intent) {
            LoginOnBoardingIntent.OnClickNextStep -> goToNextStep()
        }
    }

    private fun goToNextStep() {
        launch {
            _buttonState.emit(LoginOnBoardingState.Button.Pressed)
            delay(100L)
            _event.emit(LoginOnBoardingEvent.GoToNextStep)
        }
    }
}
