package ac.dnd.bookkeeping.android.presentation.ui.main.home.statistics.me.event

sealed interface StatisticsMeEventIntent {
    data class OnClickDateChange(
        val year: Int,
        val month: Int
    ) : StatisticsMeEventIntent
}
