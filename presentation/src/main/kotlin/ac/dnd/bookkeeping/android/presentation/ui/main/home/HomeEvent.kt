package ac.dnd.bookkeeping.android.presentation.ui.main.home

sealed interface HomeEvent {
    data class ShowSnackBar(val message: String) : HomeEvent
}
