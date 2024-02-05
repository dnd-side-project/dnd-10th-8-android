package ac.dnd.bookkeeping.android.presentation.ui.main.home.common.group.get

import ac.dnd.bookkeeping.android.domain.model.error.ServerException
import ac.dnd.bookkeeping.android.domain.usecase.feature.group.DeleteGroupUseCase
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
class GetGroupViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val deleteGroupUseCase: DeleteGroupUseCase,
) : BaseViewModel() {
    private val _state: MutableStateFlow<GetGroupState> =
        MutableStateFlow(GetGroupState.Init)
    val state: StateFlow<GetGroupState> = _state.asStateFlow()

    private val _event: MutableEventFlow<GetGroupEvent> = MutableEventFlow()
    val event: EventFlow<GetGroupEvent> = _event.asEventFlow()

    fun onIntent(intent: GetGroupIntent) {
        when (intent) {
            is GetGroupIntent.OnDelete -> deleteGroup(intent.id)
        }
    }

    private fun deleteGroup(
        id: Long
    ) {
        launch {
            _state.value = GetGroupState.Loading
            deleteGroupUseCase(
                id = id
            )
                .onSuccess {
                    _state.value = GetGroupState.Init
                    _event.emit(GetGroupEvent.DeleteGroup.Success)
                }
                .onFailure { exception ->
                    _state.value = GetGroupState.Init
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
}
