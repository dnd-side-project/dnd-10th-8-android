package ac.dnd.bookkeeping.android.presentation.ui.main.home.event.common.group

sealed interface AddGroupIntent {
    data class OnConfirm(val name: String) : AddGroupIntent
}
