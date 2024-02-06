package ac.dnd.bookkeeping.android.presentation.ui.main.home.schedule.add.notification

sealed interface ScheduleAddNotificationState {
    data object Init : ScheduleAddNotificationState
}
