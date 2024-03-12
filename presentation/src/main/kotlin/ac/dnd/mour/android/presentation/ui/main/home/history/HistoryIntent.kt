package ac.dnd.mour.android.presentation.ui.main.home.history

sealed interface HistoryIntent{
    data object LoadData : HistoryIntent
    data object HideSchedules : HistoryIntent
}
