package ac.dnd.mour.android.presentation.ui.main.home.history.unrecorded

sealed interface HistoryUnrecordedEvent {
    data object ShowNextData : HistoryUnrecordedEvent
}
