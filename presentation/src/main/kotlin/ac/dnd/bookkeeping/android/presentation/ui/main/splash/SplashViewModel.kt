package ac.dnd.bookkeeping.android.presentation.ui.main.splash

import ac.dnd.bookkeeping.android.domain.model.error.ServerException
import ac.dnd.bookkeeping.android.domain.usecase.authentication.IsLoginSucceedUseCase
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
class SplashViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val isLoginSucceedUseCase: IsLoginSucceedUseCase
) : BaseViewModel() {

    private val _state: MutableStateFlow<SplashState> = MutableStateFlow(SplashState.Init)
    val state: StateFlow<SplashState> = _state.asStateFlow()

    private val _event: MutableEventFlow<SplashEvent> = MutableEventFlow()
    val event: EventFlow<SplashEvent> = _event.asEventFlow()

    val entryPoint: String by lazy {
        savedStateHandle.get<String>(SplashConstant.ROUTE_ARGUMENT_ENTRY_POINT).orEmpty()
    }

    init {
        launch {
            login()
        }
    }

    private suspend fun login() {
        isLoginSucceedUseCase().onSuccess {
            _event.emit(SplashEvent.Login.Success)
        }.onFailure { exception ->
            when (exception) {
                is ServerException -> {
                    _event.emit(SplashEvent.Login.Failure(exception))
                }

                else -> {
                    _event.emit(SplashEvent.Login.Error(exception))
                }
            }
        }
    }
}
