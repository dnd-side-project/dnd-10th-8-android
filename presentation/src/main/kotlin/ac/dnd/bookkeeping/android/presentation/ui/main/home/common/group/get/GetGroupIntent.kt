package ac.dnd.bookkeeping.android.presentation.ui.main.home.common.group.get

sealed interface GetGroupIntent {
    data class OnDelete(val id: Long) : GetGroupIntent
}
