package ac.dnd.mour.android.presentation.ui.main.home.mypage.profile

sealed interface MyPageProfileState {
    data object Init : MyPageProfileState
    data object Loading : MyPageProfileState
}
