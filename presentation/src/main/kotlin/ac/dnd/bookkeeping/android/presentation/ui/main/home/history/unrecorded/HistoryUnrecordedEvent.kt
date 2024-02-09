package ac.dnd.bookkeeping.android.presentation.ui.main.home.history.unrecorded

sealed interface HistoryUnrecordedEvent {
    data object ShowNextData : HistoryUnrecordedEvent
}
