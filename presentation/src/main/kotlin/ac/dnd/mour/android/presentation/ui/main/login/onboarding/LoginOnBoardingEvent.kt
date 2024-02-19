package ac.dnd.mour.android.presentation.ui.main.login.onboarding

import ac.dnd.mour.android.presentation.model.login.KakaoUserInformationModel

sealed interface LoginOnBoardingEvent {
    data class Submit(
        val kakaoUserModel: KakaoUserInformationModel
    ) : LoginOnBoardingEvent
}
