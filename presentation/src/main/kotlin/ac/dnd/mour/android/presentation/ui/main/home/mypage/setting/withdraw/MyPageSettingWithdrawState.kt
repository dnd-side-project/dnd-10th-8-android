package ac.dnd.mour.android.presentation.ui.main.home.mypage.setting.withdraw

sealed interface MyPageSettingWithdrawState {
    data object Init : MyPageSettingWithdrawState
    data object Loading : MyPageSettingWithdrawState
}
