package ac.dnd.bookkeeping.android.presentation.ui.main.home.mypage

import ac.dnd.bookkeeping.android.domain.model.error.ServerException
import ac.dnd.bookkeeping.android.domain.model.member.Profile
import ac.dnd.bookkeeping.android.domain.usecase.authentication.LogoutUseCase
import ac.dnd.bookkeeping.android.domain.usecase.member.GetProfileUseCase
import ac.dnd.bookkeeping.android.presentation.common.base.BaseViewModel
import ac.dnd.bookkeeping.android.presentation.common.base.ErrorEvent
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.asEventFlow
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.datetime.LocalDate
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getProfileUseCase: GetProfileUseCase,
    private val logoutUseCase: LogoutUseCase
) : BaseViewModel() {

    private val _state: MutableStateFlow<MyPageState> = MutableStateFlow(MyPageState.Init)
    val state: StateFlow<MyPageState> = _state.asStateFlow()

    private val _event: MutableEventFlow<MyPageEvent> = MutableEventFlow()
    val event: EventFlow<MyPageEvent> = _event.asEventFlow()

    private val _profile: MutableStateFlow<Profile> = MutableStateFlow(
        Profile(
            id = 0,
            email = "",
            profileImageUrl = "",
            name = "",
            nickname = "",
            gender = "",
            birth = LocalDate(2000, 1, 1)
        )
    )
    val profile: StateFlow<Profile> = _profile.asStateFlow()

    init {
        getProfile()
    }

    fun onIntent(intent: MyPageIntent) {
        when (intent) {
            MyPageIntent.OnLogout -> logout()
        }
    }

    private fun getProfile() {
        launch {
            _state.value = MyPageState.Loading
            getProfileUseCase()
                .onSuccess {
                    _state.value = MyPageState.Init
                    _profile.value = it
                }
                .onFailure { exception ->
                    _state.value = MyPageState.Init
                    when (exception) {
                        is ServerException -> {
                            _errorEvent.emit(ErrorEvent.InvalidRequest(exception))
                        }

                        else -> {
                            _errorEvent.emit(ErrorEvent.UnavailableServer(exception))
                        }
                    }
                }
        }
    }

    private fun logout() {
        launch {
            _state.value = MyPageState.Loading
            logoutUseCase()
                .onSuccess {
                    _state.value = MyPageState.Init
                    _event.emit(MyPageEvent.Logout.Success)
                }
                .onFailure { exception ->
                    _state.value = MyPageState.Init
                    when (exception) {
                        is ServerException -> {
                            _errorEvent.emit(ErrorEvent.InvalidRequest(exception))
                        }

                        else -> {
                            _errorEvent.emit(ErrorEvent.UnavailableServer(exception))
                        }
                    }
                }
        }
    }
}
