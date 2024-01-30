package ac.dnd.bookkeeping.android.presentation.ui.main.registration.main

import ac.dnd.bookkeeping.android.domain.model.error.ServerException
import ac.dnd.bookkeeping.android.domain.usecase.authentication.RegistrationUseCase
import ac.dnd.bookkeeping.android.domain.usecase.member.CheckNicknameUseCase
import ac.dnd.bookkeeping.android.presentation.common.base.BaseViewModel
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.asEventFlow
import ac.dnd.bookkeeping.android.presentation.model.login.KakaoUserInformationModel
import ac.dnd.bookkeeping.android.presentation.ui.main.registration.main.type.RegistrationMainNamingErrorType
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class RegistrationMainViewModel @Inject constructor(
    private val checkNicknameUseCase: CheckNicknameUseCase,
    private val registrationUseCase: RegistrationUseCase,
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    private val _state: MutableStateFlow<RegistrationMainState> =
        MutableStateFlow(RegistrationMainState.Init)
    val state: StateFlow<RegistrationMainState> = _state.asStateFlow()

    private val _namingErrorType: MutableStateFlow<RegistrationMainNamingErrorType> =
        MutableStateFlow(RegistrationMainNamingErrorType.Init)
    val namingErrorType: StateFlow<RegistrationMainNamingErrorType> = _namingErrorType.asStateFlow()

    private val _event: MutableEventFlow<RegistrationMainEvent> = MutableEventFlow()
    val event: EventFlow<RegistrationMainEvent> = _event.asEventFlow()

    private val _kakaoUserInfo: MutableStateFlow<KakaoUserInformationModel> =
        MutableStateFlow(KakaoUserInformationModel(0L, "", "", ""))
    private val kakaoUserInfo: StateFlow<KakaoUserInformationModel> = _kakaoUserInfo.asStateFlow()

    fun onIntent(intent: RegistrationMainIntent) {
        when (intent) {
            is RegistrationMainIntent.OnCheckUserName -> checkUserNameValid(intent.name)
            is RegistrationMainIntent.OnClickSubmit ->{
                registerUser(
                    nickName = intent.nickName,
                    gender = intent.gender,
                    birth = intent.birth
                )
            }
        }
    }

    fun initKakaoUserInfo(userModel:KakaoUserInformationModel){
        _kakaoUserInfo.value = userModel
    }

    private fun checkUserNameValid(name: String) {
        launch {
            checkNicknameUseCase(name)
                .onSuccess {
                    if (it){
                        _event.emit(RegistrationMainEvent.Check.Valid)
                    }else {
                        _event.emit(RegistrationMainEvent.Check.Invalid)
                        _namingErrorType.value = RegistrationMainNamingErrorType.InValid.Duplication
                    }
                }
                .onFailure {
                    _namingErrorType.value = RegistrationMainNamingErrorType.Init
                }
        }
    }

    private fun registerUser(
        nickName: String,
        gender: String,
        birth: String
    ) {
        launch {
            kakaoUserInfo.value.let { userModel ->
                registrationUseCase(
                    socialId = userModel.socialId,
                    email = userModel.email,
                    profileImageUrl = userModel.profileImageUrl,
                    name =  userModel.name,
                    nickname = nickName,
                    gender = gender,
                    birth = birth
                )
                    .onSuccess {
                        _event.emit(RegistrationMainEvent.Submit.Success)
                    }
                    .onFailure { exception ->
                        when (exception) {
                            is ServerException -> {
                                _event.emit(RegistrationMainEvent.Submit.Failure(exception))
                            }

                            else -> {
                                _event.emit(RegistrationMainEvent.Submit.Error(exception))
                            }
                        }
                    }
            }
        }
    }
}
