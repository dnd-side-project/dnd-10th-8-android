package ac.dnd.mour.android.presentation.ui.main.home.history.unrecorded

sealed interface HistoryUnrecordedIntent {
    data class OnRecord(
        val scheduleId: Long,
        val money: Long,
        val tags: List<String>
    ) : HistoryUnrecordedIntent

    data class OnSkip(val scheduleId: Long) : HistoryUnrecordedIntent
}
