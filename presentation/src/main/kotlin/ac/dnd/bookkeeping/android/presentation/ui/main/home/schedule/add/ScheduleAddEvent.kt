package ac.dnd.bookkeeping.android.presentation.ui.main.home.schedule.add

sealed interface ScheduleAddEvent {
    sealed interface AddSchedule : ScheduleAddEvent {
        data object Success : AddSchedule
    }
}
