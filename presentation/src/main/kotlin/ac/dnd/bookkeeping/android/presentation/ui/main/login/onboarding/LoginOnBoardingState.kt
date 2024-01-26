package ac.dnd.bookkeeping.android.presentation.ui.main.login.onboarding

import ac.dnd.bookkeeping.android.domain.model.error.ServerException

sealed interface LoginOnBoardingState {
    data object Init : LoginOnBoardingState
    sealed interface Loading : LoginOnBoardingEvent {
        data object Success : Loading
        data class Failure(val exception: ServerException) : Loading
        data class Error(val exception: Throwable) : Loading
    }
}
