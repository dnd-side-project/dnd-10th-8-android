package ac.dnd.mour.android.presentation.ui.main.home.schedule.add

sealed interface ScheduleAddState {
    data object Init : ScheduleAddState
    data object Loading : ScheduleAddState
}
