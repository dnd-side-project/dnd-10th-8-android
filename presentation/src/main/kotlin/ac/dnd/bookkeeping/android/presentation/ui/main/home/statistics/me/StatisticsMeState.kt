package ac.dnd.bookkeeping.android.presentation.ui.main.home.statistics.me

sealed interface StatisticsMeState {
    data object Init : StatisticsMeState
    data object Loading : StatisticsMeState
}
