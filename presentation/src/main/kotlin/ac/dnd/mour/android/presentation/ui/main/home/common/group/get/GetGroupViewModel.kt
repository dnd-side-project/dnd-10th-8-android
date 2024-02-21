package ac.dnd.mour.android.presentation.ui.main.home.common.group.get

import ac.dnd.mour.android.domain.model.error.ServerException
import ac.dnd.mour.android.domain.model.feature.group.Group
import ac.dnd.mour.android.domain.usecase.feature.group.DeleteGroupUseCase
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
class GetGroupViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val deleteGroupUseCase: DeleteGroupUseCase,
) : BaseViewModel() {
    private val _state: MutableStateFlow<GetGroupState> =
        MutableStateFlow(GetGroupState.Init)
    val state: StateFlow<GetGroupState> = _state.asStateFlow()

    private val _event: MutableEventFlow<GetGroupEvent> = MutableEventFlow()
    val event: EventFlow<GetGroupEvent> = _event.asEventFlow()

    private val _groups: MutableStateFlow<List<Group>> = MutableStateFlow(emptyList())
    val group: StateFlow<List<Group>> = _groups.asStateFlow()

    fun onIntent(intent: GetGroupIntent) {
        when (intent) {
            is GetGroupIntent.OnDelete -> deleteGroup(intent.id)
            is GetGroupIntent.OnAdd -> addGroup(intent.group)
            is GetGroupIntent.OnEdit -> editGroup(intent.group)
        }
    }

    private fun deleteGroup(id: Long) {
        launch {
            _state.value = GetGroupState.Loading
            deleteGroupUseCase(id = id)
                .onSuccess {
                    _state.value = GetGroupState.Init
                    _groups.value = _groups.value.filter { it.id != id }
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

    private fun addGroup(group: Group) {
        _groups.value = _groups.value.toMutableList().also {
            it.add(group)
        }
    }

    private fun editGroup(newGroup: Group) {
        _groups.value = _groups.value.toMutableList().also {
            it.replaceAll { group ->
                if (group.id == newGroup.id) newGroup else group
            }
        }
    }
}
