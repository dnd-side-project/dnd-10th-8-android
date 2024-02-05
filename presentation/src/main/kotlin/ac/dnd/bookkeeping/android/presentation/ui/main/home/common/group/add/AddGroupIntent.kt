package ac.dnd.bookkeeping.android.presentation.ui.main.home.common.group.add

sealed interface AddGroupIntent {
    data class OnConfirm(val name: String) : AddGroupIntent
}
