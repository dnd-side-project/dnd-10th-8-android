package ac.dnd.mour.android.presentation.ui.main.login.onboarding

import ac.dnd.mour.android.presentation.ui.main.login.LoginConstant

object LoginOnBoardingConstant {
    const val ROUTE: String = "${LoginConstant.ROUTE}/onBoarding"

    const val ROURE_ARGUMENT_USER_MODEL = "kakaoUserModel"
    const val CONTAIN_USER_MODEL = "${ROUTE}/{${ROURE_ARGUMENT_USER_MODEL}}"
}
