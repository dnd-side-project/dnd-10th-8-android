package ac.dnd.bookkeeping.android.presentation.ui.main.home.history.main

import ac.dnd.bookkeeping.android.domain.model.error.ServerException

sealed interface HistoryMainEvent {
    sealed interface GetHistoryInfoMain : HistoryMainEvent {
        data class Failure(val exception: ServerException) : GetHistoryInfoMain
        data class Error(val exception: Throwable) : GetHistoryInfoMain
    }
}
