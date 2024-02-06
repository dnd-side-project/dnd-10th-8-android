package ac.dnd.bookkeeping.android.presentation.ui.main.home.common.relation.add

import ac.dnd.bookkeeping.android.domain.model.error.ServerException
import ac.dnd.bookkeeping.android.domain.model.feature.group.Group
import ac.dnd.bookkeeping.android.domain.usecase.feature.group.GetGroupListUseCase
import ac.dnd.bookkeeping.android.domain.usecase.feature.relation.AddRelationUseCase
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
class AddRelationViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getGroupListUseCase: GetGroupListUseCase,
    private val addRelationUseCase: AddRelationUseCase
) : BaseViewModel() {

    private val _state: MutableStateFlow<AddRelationState> =
        MutableStateFlow(AddRelationState.Init)
    val state: StateFlow<AddRelationState> = _state.asStateFlow()

    private val _event: MutableEventFlow<AddRelationEvent> = MutableEventFlow()
    val event: EventFlow<AddRelationEvent> = _event.asEventFlow()

    private val _groups: MutableStateFlow<List<Group>> = MutableStateFlow(emptyList())
    val groups: StateFlow<List<Group>> = _groups.asStateFlow()

    init {
        launch {
            _state.value = AddRelationState.Loading
            getGroupListUseCase()
                .onSuccess {
                    _groups.value = it
                }
                .onFailure { exception ->
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

    fun onIntent(intent: AddRelationIntent) {
        when (intent) {
            is AddRelationIntent.OnClickSubmit -> addRelation(
                intent.groupId,
                intent.name,
                intent.imageUrl,
                intent.memo
            )
        }
    }

    private fun addRelation(
        groupId: Long,
        name: String,
        imageUrl: String,
        memo: String,
    ) {
        launch {
            _state.value = AddRelationState.Loading
            addRelationUseCase(
                groupId = groupId,
                name = name,
                imageUrl = imageUrl,
                memo = memo
            )
                .onSuccess {
                    _state.value = AddRelationState.Init
                    _event.emit(AddRelationEvent.Submit.Success)
                }
                .onFailure { exception ->
                    _state.value = AddRelationState.Init
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
