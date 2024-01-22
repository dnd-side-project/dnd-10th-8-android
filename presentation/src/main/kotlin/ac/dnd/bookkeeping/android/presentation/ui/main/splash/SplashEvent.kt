package ac.dnd.bookkeeping.android.presentation.ui.main.splash

import ac.dnd.bookkeeping.android.domain.model.error.ServerException

sealed interface SplashEvent {
    sealed interface Login : SplashEvent {
        data object Success : Login
        data class Failure(val exception: ServerException) : Login
        data class Error(val exception: Throwable) : Login
    }
}
