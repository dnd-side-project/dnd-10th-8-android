package ac.dnd.mour.android.presentation.ui.main.home.history.detail

import ac.dnd.mour.android.domain.model.error.ServerException
import ac.dnd.mour.android.domain.model.feature.heart.RelatedHeart
import ac.dnd.mour.android.domain.model.feature.relation.RelationDetailGroup
import ac.dnd.mour.android.domain.model.feature.relation.RelationDetailWithUserInfo
import ac.dnd.mour.android.domain.usecase.feature.heart.GetRelatedHeartListUseCase
import ac.dnd.mour.android.domain.usecase.feature.relation.GetRelationUseCase
import ac.dnd.mour.android.presentation.common.base.BaseViewModel
import ac.dnd.mour.android.presentation.common.base.ErrorEvent
import ac.dnd.mour.android.common.coroutine.event.EventFlow
import ac.dnd.mour.android.common.coroutine.event.MutableEventFlow
import ac.dnd.mour.android.common.coroutine.event.asEventFlow
import ac.dnd.mour.android.common.coroutine.zip
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class HistoryDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getRelationUseCase: GetRelationUseCase,
    private val getRelatedHeartListUseCase: GetRelatedHeartListUseCase
) : BaseViewModel() {

    private val _state: MutableStateFlow<HistoryDetailState> =
        MutableStateFlow(HistoryDetailState.Init)
    val state: StateFlow<HistoryDetailState> = _state.asStateFlow()

    private val _event: MutableEventFlow<HistoryDetailEvent> = MutableEventFlow()
    val event: EventFlow<HistoryDetailEvent> = _event.asEventFlow()

    private val _hearts: MutableStateFlow<List<RelatedHeart>> =
        MutableStateFlow(emptyList())
    val hearts: StateFlow<List<RelatedHeart>> = _hearts.asStateFlow()

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
            zip(
                { getRelationUseCase(id) },
                { getRelatedHeartListUseCase(id, "recent") }
            )
                .onSuccess { (detail, hearts) ->
                    _state.value = HistoryDetailState.Init
                    _relationDetail.value = detail
                    _hearts.value = hearts
                }
                .onFailure { exception ->
                    _state.value = HistoryDetailState.Init
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
            is HistoryDetailIntent.OnEdit -> {
                _hearts.value = _hearts.value.map {
                    if (it.id == intent.editHeart.id) intent.editHeart else it
                }
            }

            is HistoryDetailIntent.OnDelete -> {
                _hearts.value = _hearts.value.filter { it.id != intent.deleteId }
            }
        }
    }
}
