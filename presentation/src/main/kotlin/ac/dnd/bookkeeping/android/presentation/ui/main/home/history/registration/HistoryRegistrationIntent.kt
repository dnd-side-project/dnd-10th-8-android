package ac.dnd.bookkeeping.android.presentation.ui.main.home.history.registration

import kotlinx.datetime.LocalDate

sealed interface HistoryRegistrationIntent {
    data class OnClickSubmit(
        val relationId: Long,
        val give: Boolean,
        val money: Long,
        val day: LocalDate,
        val event: String,
        val memo: String,
        val tags: List<String>
    ) : HistoryRegistrationIntent
}
