package ac.dnd.bookkeeping.android.presentation.ui.main.home.history.detail.edit

import ac.dnd.bookkeeping.android.domain.model.feature.heart.RelatedHeart
import ac.dnd.bookkeeping.android.domain.usecase.feature.heart.DeleteHeartUseCase
import ac.dnd.bookkeeping.android.domain.usecase.feature.heart.EditHeartUseCase
import ac.dnd.bookkeeping.android.presentation.common.base.BaseViewModel
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
class HistoryDetailEditViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val editHeartUseCase: EditHeartUseCase,
    private val deleteHeartUseCase: DeleteHeartUseCase
) : BaseViewModel() {

    private val _state: MutableStateFlow<HistoryDetailEditState> =
        MutableStateFlow(HistoryDetailEditState.Init)
    val state: StateFlow<HistoryDetailEditState> = _state.asStateFlow()

    private val _event: MutableEventFlow<HistoryDetailEditEvent> = MutableEventFlow()
    val event: EventFlow<HistoryDetailEditEvent> = _event.asEventFlow()

    fun onIntent(intent: HistoryDetailEditIntent) {
        when(intent){
            is HistoryDetailEditIntent.OnEdit -> {

            }
            is HistoryDetailEditIntent.OnDelete ->{

            }
        }
    }

    private fun onEdit(
        id: Long,
        give : Boolean,
        money: Long,
        day: LocalDate,
        event: String,
        memo: String,
        tags: List<String>
    ){
        launch {
            _state.value = HistoryDetailEditState.Loading
            editHeartUseCase(
                id = id,
                money = money,
                day = day,
                event =  event ,
                memo =  memo,
                tags = tags,
            )
                .onSuccess {
                    _state.value = HistoryDetailEditState.Init
                    _event.emit(
                        HistoryDetailEditEvent.EditRelatedHeart.Success(
                            RelatedHeart(
                                id = id,
                                money = money,
                                give = give,
                                day = day,
                                event = event,
                                memo = memo,
                                tags = tags
                            )
                        )
                    )
                }
                .onFailure {
                    _state.value = HistoryDetailEditState.Init
                }
        }
    }

    private fun onDelete(
        id: Long
    ){
        launch {
            _state.value = HistoryDetailEditState.Loading
            deleteHeartUseCase(
                id = id
            )
                .onSuccess {
                    _state.value = HistoryDetailEditState.Init
                    _event.emit(HistoryDetailEditEvent.DeleteRelatedHeart.Success)
                }
                .onFailure {
                    _state.value = HistoryDetailEditState.Init
                }
        }
    }
}
