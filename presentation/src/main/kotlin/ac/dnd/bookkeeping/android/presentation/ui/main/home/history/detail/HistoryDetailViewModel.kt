package ac.dnd.bookkeeping.android.presentation.ui.main.home.history.detail

import ac.dnd.bookkeeping.android.domain.model.error.ServerException
import ac.dnd.bookkeeping.android.domain.model.feature.relation.RelationDetailGroup
import ac.dnd.bookkeeping.android.domain.model.feature.relation.RelationDetailWithUserInfo
import ac.dnd.bookkeeping.android.domain.usecase.feature.relation.GetRelationUseCase
import ac.dnd.bookkeeping.android.presentation.common.base.BaseViewModel
import ac.dnd.bookkeeping.android.presentation.common.base.ErrorEvent
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.asEventFlow
import ac.dnd.bookkeeping.android.presentation.model.history.HistoryViewType
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HistoryDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getRelationUseCase: GetRelationUseCase
) : BaseViewModel() {

    private val _state: MutableStateFlow<HistoryDetailState> =
        MutableStateFlow(HistoryDetailState.Init)
    val state: StateFlow<HistoryDetailState> = _state.asStateFlow()

    private val _event: MutableEventFlow<HistoryDetailEvent> = MutableEventFlow()
    val event: EventFlow<HistoryDetailEvent> = _event.asEventFlow()

    private val _historyType: MutableStateFlow<HistoryViewType> =
        MutableStateFlow(HistoryViewType.TOTAL)
    val historyType: StateFlow<HistoryViewType> = _historyType.asStateFlow()

    private val _relationDetail: MutableStateFlow<RelationDetailWithUserInfo> =
        MutableStateFlow(
            RelationDetailWithUserInfo(
                id = -1,
                name = "",
                imageUrl = "",
                memo = "",
                group = RelationDetailGroup(
                    id = -1,
                    name = ""
                ),
                giveMoney = 0L,
                takeMoney = 0L
            )
        )
    val relationDetail: StateFlow<RelationDetailWithUserInfo> = _relationDetail.asStateFlow()

    fun loadRelationDetail(id: Long) {
        launch {
            _state.value = HistoryDetailState.Loading
            getRelationUseCase(id)
                .onSuccess {
                    _state.value = HistoryDetailState.Init
                    _relationDetail.value = it
                }.onFailure { exception ->
                    _state.value = HistoryDetailState.Loading
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

    fun onIntent(intent: HistoryDetailIntent) {
        when (intent) {
            is HistoryDetailIntent.ClickTab -> {
                _historyType.value = intent.type
            }
        }
    }
}
