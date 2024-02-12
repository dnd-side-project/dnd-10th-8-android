package ac.dnd.bookkeeping.android.presentation.ui.main.home.schedule

sealed interface ScheduleState {
    data object Init : ScheduleState
    data object Loading : ScheduleState
}
