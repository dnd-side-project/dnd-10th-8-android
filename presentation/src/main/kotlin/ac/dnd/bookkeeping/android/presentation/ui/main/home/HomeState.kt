package ac.dnd.bookkeeping.android.presentation.ui.main.home

sealed interface HomeState {
    data object Init : HomeState
}
