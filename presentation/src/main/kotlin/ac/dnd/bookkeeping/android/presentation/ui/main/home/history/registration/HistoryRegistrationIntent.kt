package ac.dnd.bookkeeping.android.presentation.ui.main.home.history.registration

sealed interface HistoryRegistrationIntent {
    data class OnClickSubmit(
        val relationId: Long,
        val give: Boolean,
        val money: Long,
        val day: String,
        val event: String,
        val memo: String?,
        val tags: List<String>?
    ) : HistoryRegistrationIntent
}
