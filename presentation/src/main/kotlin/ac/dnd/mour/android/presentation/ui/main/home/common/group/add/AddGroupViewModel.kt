package ac.dnd.mour.android.presentation.ui.main.home.common.group.add

import ac.dnd.mour.android.domain.model.error.ServerException
import ac.dnd.mour.android.domain.model.feature.group.Group
import ac.dnd.mour.android.domain.usecase.feature.group.AddGroupUseCase
import ac.dnd.mour.android.presentation.common.base.BaseViewModel
import ac.dnd.mour.android.presentation.common.base.ErrorEvent
import ac.dnd.mour.android.common.coroutine.event.EventFlow
import ac.dnd.mour.android.common.coroutine.event.MutableEventFlow
import ac.dnd.mour.android.common.coroutine.event.asEventFlow
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class AddGroupViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val addGroupUseCase: AddGroupUseCase
) : BaseViewModel() {

    private val _state: MutableStateFlow<AddGroupState> =
        MutableStateFlow(AddGroupState.Init)
    val state: StateFlow<AddGroupState> = _state.asStateFlow()

    private val _event: MutableEventFlow<AddGroupEvent> = MutableEventFlow()
    val event: EventFlow<AddGroupEvent> = _event.asEventFlow()

    fun onIntent(intent: AddGroupIntent) {
        when (intent) {
            is AddGroupIntent.OnConfirm -> onConfirm(intent.name)
        }
    }

    private fun onConfirm(
        name: String
    ) {
        launch {
            _state.value = AddGroupState.Loading
            addGroupUseCase(
                name = name
            ).onSuccess { groupId ->
                _state.value = AddGroupState.Init
                _event.emit(
                    AddGroupEvent
                        .AddGroup
                        .Success(
                            Group(
                                id = groupId,
                                name = name
                            )
                        )
                )
            }.onFailure { exception ->
                _state.value = AddGroupState.Init
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
