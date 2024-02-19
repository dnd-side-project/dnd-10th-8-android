package ac.dnd.mour.android.presentation.ui.main.home

sealed interface HomeState {
    data object Init : HomeState
}
