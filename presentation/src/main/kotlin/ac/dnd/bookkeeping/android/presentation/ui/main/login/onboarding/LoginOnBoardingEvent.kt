package ac.dnd.bookkeeping.android.presentation.ui.main.login.onboarding

import ac.dnd.bookkeeping.android.presentation.model.login.KakaoUserInformationModel

sealed interface LoginOnBoardingEvent{
    data class Submit(
        val kakaoUserModel: KakaoUserInformationModel
    ) : LoginOnBoardingEvent
}
