package ac.dnd.bookkeeping.android.presentation.ui.main.home.schedule.add

import ac.dnd.bookkeeping.android.domain.model.feature.schedule.Schedule

sealed interface ScheduleAddEvent {
    sealed interface LoadSchedule : ScheduleAddEvent {
        data class Success(val schedule: Schedule) : LoadSchedule
    }

    sealed interface AddSchedule : ScheduleAddEvent {
        data object Success : AddSchedule
    }

    sealed interface EditSchedule : ScheduleAddEvent {
        data object Success : EditSchedule
    }

    sealed interface RemoveSchedule : ScheduleAddEvent {
        data object Success : RemoveSchedule
    }
}
