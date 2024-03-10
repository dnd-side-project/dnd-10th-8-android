package ac.dnd.mour.android.presentation.ui.main.home.schedule.add

import ac.dnd.mour.android.domain.model.feature.schedule.Schedule
import kotlinx.datetime.LocalDate

sealed interface ScheduleAddEvent {
    sealed interface LoadSchedule : ScheduleAddEvent {
        data class Success(val schedule: Schedule) : LoadSchedule
    }

    sealed interface LoadLocalDate : ScheduleAddEvent {
        data class Success(val date: LocalDate) : LoadLocalDate
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
