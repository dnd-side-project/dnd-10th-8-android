package ac.dnd.bookkeeping.android.presentation.ui.main.home.history.main

import ac.dnd.bookkeeping.android.domain.model.error.ServerException
import ac.dnd.bookkeeping.android.domain.model.legacy.HistoryInfoLegacy
import ac.dnd.bookkeeping.android.domain.usecase.legacy.GetHistoryInfoUseCase
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
import javax.inject.Inject

@HiltViewModel
class HistoryMainViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getHistoryInfoUseCase: GetHistoryInfoUseCase
) : BaseViewModel() {

    private val _state: MutableStateFlow<HistoryMainState> = MutableStateFlow(HistoryMainState.Init)
    val state: StateFlow<HistoryMainState> = _state.asStateFlow()

    private val _event: MutableEventFlow<HistoryMainEvent> = MutableEventFlow()
    val event: EventFlow<HistoryMainEvent> = _event.asEventFlow()

    private val _historyInfo: MutableStateFlow<HistoryInfoLegacy> =
        MutableStateFlow(HistoryInfoLegacy(0, 0, false))
    val historyInfo: StateFlow<HistoryInfoLegacy> = _historyInfo.asStateFlow()

    init {
        launch {
            _state.value = HistoryMainState.Loading
            getHistoryInfoUseCase()
                .onSuccess {
                    _historyInfo.value = it
                }
                .onFailure { exception ->
                    when (exception) {
                        is ServerException -> {
                            _errorEvent.emit(ErrorEvent.InvalidRequest(exception))
                        }

                        else -> {
                            _errorEvent.emit(ErrorEvent.UnavailableServer(exception))
                        }
                    }
                }
            _state.value = HistoryMainState.Init
        }
    }

    fun onIntent(intent: HistoryMainIntent) {

    }
}