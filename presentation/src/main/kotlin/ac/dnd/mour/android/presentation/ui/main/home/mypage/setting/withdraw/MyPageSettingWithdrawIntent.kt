package ac.dnd.mour.android.presentation.ui.main.home.mypage.setting.withdraw

sealed interface MyPageSettingWithdrawIntent {
    data object OnWithdraw : MyPageSettingWithdrawIntent
}
