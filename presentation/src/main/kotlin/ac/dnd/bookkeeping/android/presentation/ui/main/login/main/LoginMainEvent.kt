package ac.dnd.bookkeeping.android.presentation.ui.main.login.main

import ac.dnd.bookkeeping.android.domain.model.error.ServerException
import ac.dnd.bookkeeping.android.presentation.model.login.KakaoUserInformationModel

sealed interface LoginMainEvent {
    sealed interface Login : LoginMainEvent {
        data object Success : Login
        data class RequireRegister(
            val kakaoUserModel: KakaoUserInformationModel
        ) : Login

        data class Failure(val exception: ServerException) : Login
        data class Error(val exception: Throwable) : Login
    }
}
