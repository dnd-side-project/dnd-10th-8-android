package ac.dnd.bookkeeping.android.presentation.ui.main.home.common.relation.get

import ac.dnd.bookkeeping.android.domain.model.error.ServerException
import ac.dnd.bookkeeping.android.domain.model.feature.group.GroupWithRelationSimple
import ac.dnd.bookkeeping.android.domain.usecase.feature.group.GetGroupListWithRelationSimpleUseCase
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
class GetRelationViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getGroupListWithRelationSimpleUseCase: GetGroupListWithRelationSimpleUseCase
) : BaseViewModel() {

    private val _state: MutableStateFlow<GetRelationState> =
        MutableStateFlow(GetRelationState.Init)
    val state: StateFlow<GetRelationState> = _state.asStateFlow()

    private val _event: MutableEventFlow<GetRelationEvent> = MutableEventFlow()
    val event: EventFlow<GetRelationEvent> = _event.asEventFlow()

    private val _groups: MutableStateFlow<List<GroupWithRelationSimple>> =
        MutableStateFlow(emptyList())
    val groups: StateFlow<List<GroupWithRelationSimple>> = _groups.asStateFlow()

    init {
        launch {
            _state.value = GetRelationState.Loading
            getGroupListWithRelationSimpleUseCase()
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

    fun onIntent(intent: GetRelationIntent) {

    }
}
