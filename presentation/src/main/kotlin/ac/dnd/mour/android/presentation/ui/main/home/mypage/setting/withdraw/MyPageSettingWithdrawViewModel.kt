package ac.dnd.mour.android.presentation.ui.main.home.mypage.setting.withdraw

import ac.dnd.mour.android.domain.model.error.ServerException
import ac.dnd.mour.android.domain.usecase.authentication.WithdrawUseCase
import ac.dnd.mour.android.presentation.common.base.BaseViewModel
import ac.dnd.mour.android.presentation.common.base.ErrorEvent
import ac.dnd.mour.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.mour.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.mour.android.presentation.common.util.coroutine.event.asEventFlow
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MyPageSettingWithdrawViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val withdrawUseCase: WithdrawUseCase
) : BaseViewModel() {

    private val _state: MutableStateFlow<MyPageSettingWithdrawState> =
        MutableStateFlow(MyPageSettingWithdrawState.Init)
    val state: StateFlow<MyPageSettingWithdrawState> = _state.asStateFlow()

    private val _event: MutableEventFlow<MyPageSettingWithdrawEvent> = MutableEventFlow()
    val event: EventFlow<MyPageSettingWithdrawEvent> = _event.asEventFlow()

    fun onIntent(intent: MyPageSettingWithdrawIntent) {
        when (intent) {
            is MyPageSettingWithdrawIntent.OnWithdraw -> withdraw()
        }
    }

    private fun withdraw() {
        launch {
            _state.value = MyPageSettingWithdrawState.Loading
            withdrawUseCase()
                .onSuccess {
                    _state.value = MyPageSettingWithdrawState.Init
                    _event.emit(MyPageSettingWithdrawEvent.Withdraw.Success)
                }
                .onFailure { exception ->
                    _state.value = MyPageSettingWithdrawState.Init
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
