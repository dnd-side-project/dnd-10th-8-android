package ac.dnd.mour.android.presentation.ui.main.home.mypage

sealed interface MyPageIntent {
    data object OnLogout : MyPageIntent
}
