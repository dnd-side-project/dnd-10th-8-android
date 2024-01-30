package ac.dnd.bookkeeping.android.presentation.ui.main.home.event.common.relation

import ac.dnd.bookkeeping.android.domain.model.error.ServerException

sealed interface SearchRelationEvent {
    sealed interface GetGroup : SearchRelationEvent {
        data class Failure(val exception: ServerException) : GetGroup
        data class Error(val exception: Throwable) : GetGroup
    }
}
