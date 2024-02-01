package ac.dnd.bookkeeping.android.presentation.ui.main.home.event.common.group

import ac.dnd.bookkeeping.android.domain.model.error.ServerException
import ac.dnd.bookkeeping.android.domain.usecase.member.AddGroupUseCase
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
            ).onSuccess {
                _state.value = AddGroupState.Init
                _event.emit(AddGroupEvent.AddGroup.Success)
            }.onFailure { exception ->
                _state.value = AddGroupState.Init
                when (exception) {
                    is ServerException -> {
                        _event.emit(AddGroupEvent.AddGroup.Failure(exception))
                    }

                    else -> {
                        _event.emit(AddGroupEvent.AddGroup.Error(exception))
                    }
                }
            }
        }
    }
}
