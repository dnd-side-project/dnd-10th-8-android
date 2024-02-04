package ac.dnd.bookkeeping.android.presentation.ui.main.home.history.registration

import ac.dnd.bookkeeping.android.domain.model.error.ServerException

sealed interface HistoryRegistrationEvent {
    sealed interface Submit : HistoryRegistrationEvent {
        data object Success : Submit
        data class Failure(val exception: ServerException) : Submit
        data class Error(val exception: Throwable) : Submit
    }
}
