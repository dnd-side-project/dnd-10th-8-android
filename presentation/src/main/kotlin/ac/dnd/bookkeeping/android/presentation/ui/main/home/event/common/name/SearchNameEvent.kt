package ac.dnd.bookkeeping.android.presentation.ui.main.home.event.common.name

import ac.dnd.bookkeeping.android.domain.model.error.ServerException

sealed interface SearchNameEvent {
    sealed interface Login : SearchNameEvent {
        data object Success : Login
        data class Failure(val exception: ServerException) : Login
        data class Error(val exception: Throwable) : Login
    }
}
