package ac.dnd.bookkeeping.android.presentation.ui.main.home.event.common.name

sealed interface SearchNameState {
    data object Init : SearchNameState
}
