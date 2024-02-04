package ac.dnd.bookkeeping.android.presentation.ui.main.home.history.main.detail

import ac.dnd.bookkeeping.android.domain.model.error.ServerException
import ac.dnd.bookkeeping.android.domain.model.legacy.GroupLegacy
import ac.dnd.bookkeeping.android.domain.usecase.legacy.GetHistoryGroupListUseCase
import ac.dnd.bookkeeping.android.presentation.common.base.BaseViewModel
import ac.dnd.bookkeeping.android.presentation.common.base.ErrorEvent
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.asEventFlow
import ac.dnd.bookkeeping.android.presentation.ui.main.home.history.main.detail.type.HistoryViewType
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HistoryDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getHistoryGroupListUseCase: GetHistoryGroupListUseCase
) : BaseViewModel() {

    private val _state: MutableStateFlow<HistoryDetailState> =
        MutableStateFlow(HistoryDetailState.Init)
    val state: StateFlow<HistoryDetailState> = _state.asStateFlow()

    private val _event: MutableEventFlow<HistoryDetailEvent> = MutableEventFlow()
    val event: EventFlow<HistoryDetailEvent> = _event.asEventFlow()

    private val _totalGroups: MutableStateFlow<List<GroupLegacy>> = MutableStateFlow(emptyList())
    val totalGroups: StateFlow<List<GroupLegacy>> = _totalGroups.asStateFlow()

    private val _takeGroups: MutableStateFlow<List<GroupLegacy>> = MutableStateFlow(emptyList())
    val takeGroups: StateFlow<List<GroupLegacy>> = _takeGroups.asStateFlow()

    private val _giveGroups: MutableStateFlow<List<GroupLegacy>> = MutableStateFlow(emptyList())
    val giveGroups: StateFlow<List<GroupLegacy>> = _giveGroups.asStateFlow()

    init {
        HistoryViewType.entries.forEach {
            loadHistoryData(it)
        }
    }

    private fun loadHistoryData(historyViewType: HistoryViewType) {
        launch {
            _state.value = HistoryDetailState.Loading
            getHistoryGroupListUseCase(historyViewType.typeName)
                .onSuccess {
                    when (historyViewType) {
                        HistoryViewType.TOTAL -> _totalGroups.value = it
                        HistoryViewType.TAKE -> _takeGroups.value = it
                        HistoryViewType.GIVE -> _giveGroups.value = it
                    }
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
            _state.value = HistoryDetailState.Init
        }
    }
}
