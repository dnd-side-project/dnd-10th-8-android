package ac.dnd.mour.android.presentation.ui.main.home.mypage.profile

sealed interface MyPageProfileEvent {
    sealed interface Edit : MyPageProfileEvent {
        data object Success : Edit
    }
}
