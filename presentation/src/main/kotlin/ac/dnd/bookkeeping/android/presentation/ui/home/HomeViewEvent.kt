package ac.dnd.bookkeeping.android.presentation.ui.home

sealed class HomeViewEvent {
    data object Confirm : HomeViewEvent()
}
