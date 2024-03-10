package ac.dnd.mour.android.presentation.ui.main.login.main

import ac.dnd.mour.android.domain.model.error.ServerException
import ac.dnd.mour.android.domain.usecase.authentication.LoginUseCase
import ac.dnd.mour.android.domain.usecase.authentication.sociallogin.GetKakaoUserInfoUseCase
import ac.dnd.mour.android.domain.usecase.authentication.sociallogin.LoginKakaoUseCase
import ac.dnd.mour.android.presentation.common.base.BaseViewModel
import ac.dnd.mour.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.mour.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.mour.android.presentation.common.util.coroutine.event.asEventFlow
import ac.dnd.mour.android.presentation.model.login.KakaoUserInformationModel
import ac.dnd.mour.android.presentation.model.login.toUiModel
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginMainViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val loginKakaoUseCase: LoginKakaoUseCase,
    private val getKakaoUserInfoUseCase: GetKakaoUserInfoUseCase,
    private val loginUseCase: LoginUseCase
) : BaseViewModel() {

    private val _state: MutableStateFlow<LoginMainState> = MutableStateFlow(LoginMainState.Init)
    val state: StateFlow<LoginMainState> = _state.asStateFlow()

    private val _event: MutableEventFlow<LoginMainEvent> = MutableEventFlow()
    val event: EventFlow<LoginMainEvent> = _event.asEventFlow()

    private val _kakaoUserInfo: MutableStateFlow<KakaoUserInformationModel> =
        MutableStateFlow(KakaoUserInformationModel(0L, "", "", ""))
    private val kakaoUserInfo: StateFlow<KakaoUserInformationModel> = _kakaoUserInfo.asStateFlow()

    fun onIntent(intent: LoginMainIntent) {
        when (intent) {
            LoginMainIntent.Click -> loginFlow()
        }
    }

    private fun loginFlow() {
        launch {
            _state.emit(LoginMainState.Loading)
            getUserInfo()
        }
    }

    private fun getUserInfo() = launch {
        getKakaoUserInfoUseCase.invoke()
            .onSuccess { userInfo ->
                _kakaoUserInfo.emit(userInfo.toUiModel())
                login(
                    userEmail = userInfo.email,
                    userSocialId = userInfo.socialId
                )
            }
            .onFailure { error ->
                when (error) {
                    is ServerException -> {
                        _event.emit(LoginMainEvent.Login.Failure(error))
                    }

                    else -> {
                        _event.emit(LoginMainEvent.Login.Error(error))
                    }
                }
                _state.emit(LoginMainState.Init)
            }
    }

    private fun login(
        userSocialId: Long,
        userEmail: String,
    ) {
        launch {
            _state.value = LoginMainState.Loading
            loginUseCase.invoke(
                socialId = userSocialId,
                email = userEmail
            )
                .onSuccess {
                    _state.value = LoginMainState.Init
                    _event.emit(LoginMainEvent.Login.Success)
                }
                .onFailure { error ->
                    _state.value = LoginMainState.Init
                    when (error) {
                        is ServerException -> {
                            _event.emit(LoginMainEvent.Login.RequireRegister(kakaoUserModel = kakaoUserInfo.value))
                        }

                        else -> {
                            _event.emit(LoginMainEvent.Login.Error(error))
                        }
                    }
                }
        }
    }
}
