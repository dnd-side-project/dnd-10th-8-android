package ac.dnd.bookkeeping.android.presentation.ui.main.home.mypage.setting.withdraw

sealed interface MyPageSettingWithdrawEvent {
    sealed interface Withdraw : MyPageSettingWithdrawEvent {
        data object Success : Withdraw
    }
}
