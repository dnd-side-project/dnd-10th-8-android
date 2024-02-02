package ac.dnd.bookkeeping.android.presentation.ui.main.home.history.main.detail

import ac.dnd.bookkeeping.android.domain.model.error.ServerException

sealed interface HistoryDetailEvent {
    sealed interface GetHistoryRelationList : HistoryDetailEvent {
        data class Failure(val exception: ServerException) : GetHistoryRelationList
        data class Error(val exception: Throwable) : GetHistoryRelationList
    }
}
