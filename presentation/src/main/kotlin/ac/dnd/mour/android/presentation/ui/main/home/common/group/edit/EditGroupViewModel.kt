package ac.dnd.mour.android.presentation.ui.main.home.common.group.edit

import ac.dnd.mour.android.domain.model.error.ServerException
import ac.dnd.mour.android.domain.model.feature.group.Group
import ac.dnd.mour.android.domain.usecase.feature.group.EditGroupUseCase
import ac.dnd.mour.android.presentation.common.base.BaseViewModel
import ac.dnd.mour.android.presentation.common.base.ErrorEvent
import ac.dnd.mour.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.mour.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.mour.android.presentation.common.util.coroutine.event.asEventFlow
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class EditGroupViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val editGroupUseCase: EditGroupUseCase
) : BaseViewModel() {
    private val _state: MutableStateFlow<EditGroupState> =
        MutableStateFlow(EditGroupState.Init)
    val state: StateFlow<EditGroupState> = _state.asStateFlow()

    private val _event: MutableEventFlow<EditGroupEvent> = MutableEventFlow()
    val event: EventFlow<EditGroupEvent> = _event.asEventFlow()

    fun onIntent(intent: EditGroupIntent) {
        when (intent) {
            is EditGroupIntent.OnEdit -> {
                onEdit(
                    groupId = intent.groupId,
                    name = intent.name
                )
            }
        }
    }

    private fun onEdit(
        groupId: Long,
        name: String
    ) {
        launch {
            _state.value = EditGroupState.Loading
            editGroupUseCase(
                id = groupId,
                name = name
            )
                .onSuccess {
                    _state.value = EditGroupState.Init
                    _event.emit(
                        EditGroupEvent
                            .EditGroup
                            .Success(
                                Group(
                                    id = groupId,
                                    name = name
                                )
                            )
                    )
                }
                .onFailure { exception ->
                    _state.value = EditGroupState.Init
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
