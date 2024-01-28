package ac.dnd.bookkeeping.android.presentation.ui.main.login.onboarding

import ac.dnd.bookkeeping.android.domain.model.error.ServerException

sealed interface LoginOnBoardingState {
    sealed interface Loading {
        data object Progress : Loading
        data object Success : Loading
        data class Failure(val exception: ServerException) : Loading
    }

    sealed interface Button {
        data object Default : Button
        data object Pressed : Button
    }
}
