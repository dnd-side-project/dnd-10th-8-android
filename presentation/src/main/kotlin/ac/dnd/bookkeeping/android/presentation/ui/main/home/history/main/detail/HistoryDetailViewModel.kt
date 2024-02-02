package ac.dnd.bookkeeping.android.presentation.ui.main.home.history.main.detail

import ac.dnd.bookkeeping.android.domain.model.error.ServerException
import ac.dnd.bookkeeping.android.domain.model.event.Group
import ac.dnd.bookkeeping.android.domain.usecase.history.GetHistoryGroupListUseCase
import ac.dnd.bookkeeping.android.presentation.common.base.BaseViewModel
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.asEventFlow
import ac.dnd.bookkeeping.android.presentation.ui.main.home.event.common.relation.SearchRelationEvent
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

    private val _event: MutableEventFlow<HistoryDetailEvent > = MutableEventFlow()
    val event: EventFlow<HistoryDetailEvent> = _event.asEventFlow()

    private val _groups: MutableStateFlow<List<Group>> = MutableStateFlow(emptyList())
    val groups: StateFlow<List<Group>> = _groups.asStateFlow()

    fun loadHistoryData(
        historyViewType: HistoryViewType
    ){
        launch {
            _state.value = HistoryDetailState.Loading
            getHistoryGroupListUseCase(historyViewType.typeName)
                .onSuccess {
                    _groups.value = it
                }
                .onFailure { exception ->
                    when (exception) {
                        is ServerException -> {
                            _event.emit(HistoryDetailEvent.GetHistoryRelationList.Failure(exception))
                        }

                        else -> {
                            _event.emit(HistoryDetailEvent.GetHistoryRelationList.Error(exception))
                        }
                    }
                }
            _state.value = HistoryDetailState.Init
        }
    }
}
