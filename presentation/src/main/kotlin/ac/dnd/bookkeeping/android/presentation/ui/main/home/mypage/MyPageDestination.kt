package ac.dnd.bookkeeping.android.presentation.ui.main.home.mypage

import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import ac.dnd.bookkeeping.android.presentation.ui.main.home.mypage.setting.withdraw.myPageSettingWithdrawDestination
import androidx.navigation.NavGraphBuilder

fun NavGraphBuilder.myPageDestination(
    appState: ApplicationState
) {
    myPageSettingWithdrawDestination(appState)
}
