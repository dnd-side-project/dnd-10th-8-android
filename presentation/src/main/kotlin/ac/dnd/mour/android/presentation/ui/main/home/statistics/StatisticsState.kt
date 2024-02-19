package ac.dnd.mour.android.presentation.ui.main.home.statistics

sealed interface StatisticsState {
    data object Init : StatisticsState
}
