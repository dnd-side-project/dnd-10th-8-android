package ac.dnd.mour.android.presentation.ui.main.home.mypage.profile

import ac.dnd.mour.android.presentation.ui.main.home.mypage.MyPageConstant

object MyPageProfileConstant {
    const val ROUTE: String = "${MyPageConstant.ROUTE}/profile"

    const val ROURE_ARGUMENT_USER_MODEL = "profileModel"
    const val CONTAIN_USER_MODEL = "${ROUTE}/{${ROURE_ARGUMENT_USER_MODEL}}"
}
