package ac.dnd.bookkeeping.android.presentation.ui.main.home.statistics.me

sealed interface StatisticsMeIntent {
    data class OnClickDateChange(
        val year: Int,
        val month: Int
    ) : StatisticsMeIntent
}
