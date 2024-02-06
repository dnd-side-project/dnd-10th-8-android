package ac.dnd.bookkeeping.android.presentation.ui.main.home.history.detail.edit

import kotlinx.datetime.LocalDate

sealed interface HistoryDetailEditIntent{
    data class OnEdit(
        val id: Long,
        val money: Long,
        val give : Boolean,
        val day: LocalDate,
        val event: String,
        val memo: String,
        val tags: List<String>
    ): HistoryDetailEditIntent

    data class OnDelete(
        val id: Long,
    ): HistoryDetailEditIntent
}
