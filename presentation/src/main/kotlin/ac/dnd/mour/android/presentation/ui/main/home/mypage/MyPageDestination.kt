package ac.dnd.mour.android.presentation.ui.main.home.mypage

import ac.dnd.mour.android.presentation.ui.main.ApplicationState
import ac.dnd.mour.android.presentation.ui.main.home.mypage.profile.myPageProfileDestination
import ac.dnd.mour.android.presentation.ui.main.home.mypage.setting.withdraw.myPageSettingWithdrawDestination
import androidx.navigation.NavGraphBuilder

fun NavGraphBuilder.myPageDestination(
    appState: ApplicationState
) {
    myPageSettingWithdrawDestination(appState)
    myPageProfileDestination(appState)
}
