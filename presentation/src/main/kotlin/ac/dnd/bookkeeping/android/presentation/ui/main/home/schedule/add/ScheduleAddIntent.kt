package ac.dnd.bookkeeping.android.presentation.ui.main.home.schedule.add

sealed interface ScheduleAddIntent {
    data object OnConfirm : ScheduleAddIntent
}
