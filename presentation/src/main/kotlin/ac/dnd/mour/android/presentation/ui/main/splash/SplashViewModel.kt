package ac.dnd.mour.android.presentation.ui.main.splash

import ac.dnd.mour.android.domain.model.error.ServerException
import ac.dnd.mour.android.domain.usecase.authentication.UpdateJwtTokenUseCase
import ac.dnd.mour.android.domain.usecase.feature.schedule.GetAlarmListUseCase
import ac.dnd.mour.android.presentation.common.base.BaseViewModel
import ac.dnd.mour.android.presentation.common.base.ErrorEvent
import ac.dnd.mour.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.mour.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.mour.android.presentation.common.util.coroutine.event.asEventFlow
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val updateJwtTokenUseCase: UpdateJwtTokenUseCase,
    private val getAlarmListUseCase: GetAlarmListUseCase
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
            checkJwtToken()
        }
    }

    fun onIntent(intent: SplashIntent) {

    }

    private suspend fun checkJwtToken() {
        updateJwtTokenUseCase().map {
            getAlarmListUseCase().getOrThrow()
        }.onSuccess { alarmList ->
            _event.emit(SplashEvent.Login.Success(alarmList))
        }.onFailure { exception ->
            when (exception) {
                is ServerException -> {
                    _event.emit(SplashEvent.Login.Failure(exception))
                }

                else -> {
                    _errorEvent.emit(ErrorEvent.UnavailableServer(exception))
                }
            }
        }
    }
}
