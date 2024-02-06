package ac.dnd.bookkeeping.android.presentation.ui.main.home.statistics

sealed interface StatisticsState {
    data object Init : StatisticsState
}
