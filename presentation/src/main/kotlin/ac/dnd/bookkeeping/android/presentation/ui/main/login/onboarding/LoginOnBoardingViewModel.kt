package ac.dnd.bookkeeping.android.presentation.ui.main.login.onboarding

import ac.dnd.bookkeeping.android.presentation.common.base.BaseViewModel
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.asEventFlow
import ac.dnd.bookkeeping.android.presentation.model.login.KakaoUserInformationModel
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginOnBoardingViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    private val _state: MutableStateFlow<LoginOnBoardingState> =
        MutableStateFlow(LoginOnBoardingState.Init)
    val state: StateFlow<LoginOnBoardingState> = _state.asStateFlow()

    private val _event: MutableEventFlow<LoginOnBoardingEvent> = MutableEventFlow()
    val event: EventFlow<LoginOnBoardingEvent> = _event.asEventFlow()

    private val _kakaoUserInfo: MutableStateFlow<KakaoUserInformationModel> =
        MutableStateFlow(KakaoUserInformationModel(0L, "", "", ""))
    private val kakaoUserInfo: StateFlow<KakaoUserInformationModel> = _kakaoUserInfo.asStateFlow()

    fun initKakaoUserInfo(userModel: KakaoUserInformationModel) {
        _kakaoUserInfo.value = userModel
    }

    fun onIntent(intent: LoginOnBoardingIntent) {
        when (intent) {
            LoginOnBoardingIntent.Click -> {
                launch {
                    _event.emit(LoginOnBoardingEvent.Submit(kakaoUserInfo.value))
                }
            }
        }
    }
}
