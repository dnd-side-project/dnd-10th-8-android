package ac.dnd.bookkeeping.android.presentation.ui.main.home.mypage

sealed interface MyPageEvent {
    sealed interface Logout : MyPageEvent {
        data object Success : Logout
    }
}
