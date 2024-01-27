package ac.dnd.bookkeeping.android.presentation.ui.main.login.main

import ac.dnd.bookkeeping.android.domain.usecase.authentication.sociallogin.GetKakaoUserInfoUseCase
import ac.dnd.bookkeeping.android.domain.usecase.authentication.sociallogin.LoginKakaoUseCase
import ac.dnd.bookkeeping.android.presentation.common.base.BaseViewModel
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.asEventFlow
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginMainViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val loginKakaoUseCase: LoginKakaoUseCase,
    private val getKakaoUserInfoUseCase: GetKakaoUserInfoUseCase
) : BaseViewModel() {

    private val _state: MutableStateFlow<LoginMainState> = MutableStateFlow(LoginMainState.Init)
    val state: StateFlow<LoginMainState> = _state.asStateFlow()

    private val _event: MutableEventFlow<LoginMainEvent> = MutableEventFlow()
    val event: EventFlow<LoginMainEvent> = _event.asEventFlow()

    fun onIntent(intent: LoginMainIntent) {
        when (intent) {
            LoginMainIntent.Click -> {

            }
        }
    }

    private fun loginKakao() = launch {
        loginKakaoUseCase.invoke()
            .onSuccess {
                getUserInfo()
                Timber.d("user token(for check login state): $it")
            }
            .onFailure {
                Timber.d("login error: ${it.message}")
            }
    }

    private fun getUserInfo() = launch {
        getKakaoUserInfoUseCase.invoke()
            .onSuccess {

                Timber.d("user email: ${it.email}")
                Timber.d("user id: ${it.socialId}")
                Timber.d("user name: ${it.name}")
            }
            .onFailure {
                Timber.d("login error: ${it.message}")
            }
    }
}
