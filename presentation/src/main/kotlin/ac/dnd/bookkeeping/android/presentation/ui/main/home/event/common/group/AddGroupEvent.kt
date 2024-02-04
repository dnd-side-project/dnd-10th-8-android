package ac.dnd.bookkeeping.android.presentation.ui.main.home.event.common.group

sealed interface AddGroupEvent {
    sealed interface AddGroup : AddGroupEvent {
        data object Success : AddGroup
    }
}
