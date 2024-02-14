package ac.dnd.bookkeeping.android.presentation.ui.main.home.statistics.user

sealed interface StatisticsUserState {
    data object Init : StatisticsUserState
    data object Loading : StatisticsUserState
}
