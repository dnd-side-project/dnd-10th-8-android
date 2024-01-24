package ac.dnd.bookkeeping.android.presentation.ui.main.login.onboarding

import ac.dnd.bookkeeping.android.domain.model.error.ServerException

sealed interface LoginOnBoardingEvent {
    data object OnClickNextStep : LoginOnBoardingEvent

    sealed interface Loading : LoginOnBoardingEvent {
        data object Success : Loading
        data class Failure(val exception: ServerException) : Loading
        data class Error(val exception: Throwable) : Loading
    }
}
