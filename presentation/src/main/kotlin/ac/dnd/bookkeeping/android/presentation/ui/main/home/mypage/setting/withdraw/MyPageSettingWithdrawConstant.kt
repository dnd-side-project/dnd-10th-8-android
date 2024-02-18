package ac.dnd.bookkeeping.android.presentation.ui.main.home.mypage.setting.withdraw

import ac.dnd.bookkeeping.android.presentation.ui.main.home.mypage.setting.MyPageSettingConstant

object MyPageSettingWithdrawConstant {
    const val ROUTE: String = "${MyPageSettingConstant.ROUTE}/withdraw"

    const val ROUTE_ARGUMENT_NAME = "nickname"
    const val CONTAIN_NAME_MODEL =
        "${ROUTE}?${ROUTE_ARGUMENT_NAME}={${ROUTE_ARGUMENT_NAME}}"
}
