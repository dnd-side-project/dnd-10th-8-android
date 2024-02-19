package ac.dnd.mour.android.presentation.ui.main.home.common.group.get

sealed interface GetGroupEvent {
    sealed interface DeleteGroup : GetGroupEvent {
        data object Success : DeleteGroup
    }
}
