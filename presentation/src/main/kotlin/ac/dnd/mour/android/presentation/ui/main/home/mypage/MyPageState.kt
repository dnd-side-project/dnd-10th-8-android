package ac.dnd.mour.android.presentation.ui.main.home.mypage

sealed interface MyPageState {
    data object Init : MyPageState
    data object Loading : MyPageState
}
