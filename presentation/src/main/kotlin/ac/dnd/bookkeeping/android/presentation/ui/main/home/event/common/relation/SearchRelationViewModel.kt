package ac.dnd.bookkeeping.android.presentation.ui.main.home.event.common.relation

import ac.dnd.bookkeeping.android.domain.model.error.ServerException
import ac.dnd.bookkeeping.android.domain.model.event.Group
import ac.dnd.bookkeeping.android.domain.usecase.member.CheckNicknameUseCase
import ac.dnd.bookkeeping.android.domain.usecase.member.GetGroupListUseCase
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
class SearchRelationViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getGroupListUseCase: GetGroupListUseCase,
    private val checkNicknameUseCase: CheckNicknameUseCase
) : BaseViewModel() {

    private val _state: MutableStateFlow<SearchRelationState> =
        MutableStateFlow(SearchRelationState.Init)
    val state: StateFlow<SearchRelationState> = _state.asStateFlow()

    private val _event: MutableEventFlow<SearchRelationEvent> = MutableEventFlow()
    val event: EventFlow<SearchRelationEvent> = _event.asEventFlow()

    private val _groups: MutableStateFlow<List<Group>> = MutableStateFlow(emptyList())
    val groups: StateFlow<List<Group>> = _groups.asStateFlow()

    init {
        launch {
            _state.value = SearchRelationState.Loading
            getGroupListUseCase()
                .onSuccess {
                    _groups.value = it
                }.onFailure { exception ->
                    when (exception) {
                        is ServerException -> {
                            _event.emit(SearchRelationEvent.GetGroup.Failure(exception))
                        }

                        else -> {
                            _event.emit(SearchRelationEvent.GetGroup.Error(exception))
                        }
                    }
                }
        }
    }

    fun onIntent(intent: SearchRelationIntent) {

    }
}
