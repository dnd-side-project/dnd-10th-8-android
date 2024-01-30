package ac.dnd.bookkeeping.android.presentation.ui.main.registration.main

import ac.dnd.bookkeeping.android.presentation.ui.main.registration.RegistrationConstant

object RegistrationMainConstant {
    const val ROUTE: String = "${RegistrationConstant.ROUTE}/main"

    const val ROURE_ARGUMENT_USER_MODEL = "kakaoUserModel"
    const val CONTAIN_USER_MODEL = "${ROUTE}/{${ROURE_ARGUMENT_USER_MODEL}}"
}
