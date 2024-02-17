package ac.dnd.bookkeeping.android.presentation.ui.main.home.common.relation

import ac.dnd.bookkeeping.android.domain.model.error.ServerException
import ac.dnd.bookkeeping.android.domain.model.feature.group.Group
import ac.dnd.bookkeeping.android.domain.usecase.feature.group.GetGroupListUseCase
import ac.dnd.bookkeeping.android.domain.usecase.feature.relation.AddRelationUseCase
import ac.dnd.bookkeeping.android.domain.usecase.feature.relation.AddRelationWithUploadUseCase
import ac.dnd.bookkeeping.android.domain.usecase.feature.relation.DeleteRelationUseCase
import ac.dnd.bookkeeping.android.domain.usecase.feature.relation.EditRelationUseCase
import ac.dnd.bookkeeping.android.domain.usecase.feature.relation.EditRelationWithUploadUseCase
import ac.dnd.bookkeeping.android.domain.usecase.feature.relation.GetKakaoFriendInfoUseCase
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
class RelationViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getGroupListUseCase: GetGroupListUseCase,
    private val getKakaoFriendInfoUseCase: GetKakaoFriendInfoUseCase,
    private val addRelationUseCase: AddRelationUseCase,
    private val addRelationWithUploadUseCase: AddRelationWithUploadUseCase,
    private val editRelationUseCase: EditRelationUseCase,
    private val editRelationWithUploadUseCase: EditRelationWithUploadUseCase,
    private val deleteRelationUseCase: DeleteRelationUseCase
) : BaseViewModel() {

    private val _state: MutableStateFlow<RelationState> =
        MutableStateFlow(RelationState.Init)
    val state: StateFlow<RelationState> = _state.asStateFlow()

    private val _event: MutableEventFlow<RelationEvent> = MutableEventFlow()
    val event: EventFlow<RelationEvent> = _event.asEventFlow()

    private val _groups: MutableStateFlow<List<Group>> = MutableStateFlow(emptyList())
    val groups: StateFlow<List<Group>> = _groups.asStateFlow()

    init {
        launch {
            _state.value = RelationState.Loading
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

    fun onIntent(intent: RelationIntent) {
        when (intent) {
            is RelationIntent.OnClickAdd -> addRelation(
                intent.groupId,
                intent.name,
                intent.imageUrl,
                intent.memo
            )

            is RelationIntent.OnClickAddWithUpload -> addWithUploadRelation(
                intent.groupId,
                intent.name,
                intent.imageUrl,
                intent.fileName,
                intent.memo
            )

            is RelationIntent.OnClickEdit -> editRelation(
                intent.id,
                intent.groupId,
                intent.name,
                intent.imageUrl,
                intent.memo
            )

            is RelationIntent.OnClickEditWithUpload -> editWithUploadRelation(
                intent.id,
                intent.groupId,
                intent.name,
                intent.imageUrl,
                intent.fileName,
                intent.memo
            )

            is RelationIntent.OnClickDelete -> deleteRelation(
                intent.id
            )

            is RelationIntent.OnClickLoadFriend -> loadKakaoFriend()

            is RelationIntent.OnGroupChange -> resetGroup(intent.groups)
        }
    }

    private fun addRelation(
        groupId: Long,
        name: String,
        imageUrl: String,
        memo: String,
    ) {
        launch {
            _state.value = RelationState.Loading
            addRelationUseCase(
                groupId = groupId,
                name = name,
                imageUrl = imageUrl,
                memo = memo
            )
                .onSuccess {
                    _state.value = RelationState.Init
                    _event.emit(RelationEvent.AddRelation.Success)
                }
                .onFailure { exception ->
                    _state.value = RelationState.Init
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

    private fun addWithUploadRelation(
        groupId: Long,
        name: String,
        imageUrl: String,
        fileName: String,
        memo: String,
    ) {
        launch {
            _state.value = RelationState.Loading
            addRelationWithUploadUseCase(
                groupId = groupId,
                name = name,
                imageUrl = imageUrl,
                imageName = fileName,
                memo = memo
            )
                .onSuccess {
                    _state.value = RelationState.Init
                    _event.emit(RelationEvent.AddRelation.Success)
                }
                .onFailure { exception ->
                    _state.value = RelationState.Init
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

    private fun editRelation(
        id: Long,
        groupId: Long,
        name: String,
        imageUrl: String,
        memo: String
    ) {
        launch {
            _state.value = RelationState.Loading
            editRelationUseCase(
                id = id,
                groupId = groupId,
                name = name,
                imageUrl = imageUrl,
                memo = memo
            )
                .onSuccess {
                    _state.value = RelationState.Init
                    _event.emit(RelationEvent.EditRelation.Success)
                }
                .onFailure { exception ->
                    _state.value = RelationState.Init
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

    private fun editWithUploadRelation(
        id: Long,
        groupId: Long,
        name: String,
        imageUrl: String,
        fileName: String,
        memo: String
    ) {
        launch {
            _state.value = RelationState.Loading
            editRelationWithUploadUseCase(
                id = id,
                groupId = groupId,
                name = name,
                imageUrl = imageUrl,
                imageName = fileName,
                memo = memo
            )
                .onSuccess {
                    _state.value = RelationState.Init
                    _event.emit(RelationEvent.EditRelation.Success)
                }
                .onFailure { exception ->
                    _state.value = RelationState.Init
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

    private fun deleteRelation(
        id: Long
    ) {
        launch {
            _state.value = RelationState.Loading
            deleteRelationUseCase(id = id)
                .onSuccess {
                    _state.value = RelationState.Init
                    _event.emit(RelationEvent.DeleteRelation.Success)
                }
                .onFailure { exception ->
                    _state.value = RelationState.Init
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

    private fun loadKakaoFriend() {
        launch {
            _state.value = RelationState.Loading
            getKakaoFriendInfoUseCase()
                .onSuccess {
                    _state.value = RelationState.Init
                    _event.emit(
                        RelationEvent.LoadKakaoFriend.Success(
                            name = it.name,
                            imageUrl = it.profileImageUrl
                        )
                    )
                }
                .onFailure {
                    _state.value = RelationState.Init
                }
        }
    }

    private fun resetGroup(newGroups: List<Group>) {
        _groups.value = newGroups
    }
}
