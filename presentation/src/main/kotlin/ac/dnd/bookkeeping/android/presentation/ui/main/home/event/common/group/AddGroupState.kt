package ac.dnd.bookkeeping.android.presentation.ui.main.home.event.common.group

sealed interface AddGroupState {
    data object Init : AddGroupState
    data object Loading : AddGroupState
}
