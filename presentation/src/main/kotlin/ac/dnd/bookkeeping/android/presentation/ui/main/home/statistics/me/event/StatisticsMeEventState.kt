package ac.dnd.bookkeeping.android.presentation.ui.main.home.statistics.me.event

sealed interface StatisticsMeEventState {
    data object Init : StatisticsMeEventState
    data object Loading : StatisticsMeEventState
}
