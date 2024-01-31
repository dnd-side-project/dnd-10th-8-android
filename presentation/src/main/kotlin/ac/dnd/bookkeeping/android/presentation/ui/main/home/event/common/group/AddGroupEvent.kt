package ac.dnd.bookkeeping.android.presentation.ui.main.home.event.common.group

import ac.dnd.bookkeeping.android.domain.model.error.ServerException

sealed interface AddGroupEvent {
    sealed interface AddGroup : AddGroupEvent {
        data object Success : AddGroup
        data class Failure(val exception: ServerException) : AddGroup
        data class Error(val exception: Throwable) : AddGroup
    }
}
