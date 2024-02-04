package ac.dnd.bookkeeping.android.presentation.ui.main.home.event.common.relation

import ac.dnd.bookkeeping.android.domain.model.error.ServerException
import ac.dnd.bookkeeping.android.domain.model.feature.group.GroupWithRelation
import ac.dnd.bookkeeping.android.domain.usecase.feature.group.GetGroupListWithRelationUseCase
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
class SearchRelationViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getGroupListWithRelationUseCase: GetGroupListWithRelationUseCase
) : BaseViewModel() {

    private val _state: MutableStateFlow<SearchRelationState> =
        MutableStateFlow(SearchRelationState.Init)
    val state: StateFlow<SearchRelationState> = _state.asStateFlow()

    private val _event: MutableEventFlow<SearchRelationEvent> = MutableEventFlow()
    val event: EventFlow<SearchRelationEvent> = _event.asEventFlow()

    private val _groups: MutableStateFlow<List<GroupWithRelation>> = MutableStateFlow(emptyList())
    val groups: StateFlow<List<GroupWithRelation>> = _groups.asStateFlow()

    init {
        launch {
            _state.value = SearchRelationState.Loading
            getGroupListWithRelationUseCase()
                .onSuccess {
                    _groups.value = it
                }.onFailure { exception ->
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

    fun onIntent(intent: SearchRelationIntent) {

    }
}
