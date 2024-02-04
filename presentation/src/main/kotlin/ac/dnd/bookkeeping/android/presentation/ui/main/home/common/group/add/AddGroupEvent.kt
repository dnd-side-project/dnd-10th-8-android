package ac.dnd.bookkeeping.android.presentation.ui.main.home.common.group.add

sealed interface AddGroupEvent {
    sealed interface AddGroup : AddGroupEvent {
        data object Success : AddGroup
    }
}
