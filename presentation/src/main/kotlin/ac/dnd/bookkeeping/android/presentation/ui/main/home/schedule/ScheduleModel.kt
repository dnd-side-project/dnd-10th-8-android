package ac.dnd.bookkeeping.android.presentation.ui.main.home.schedule

import ac.dnd.bookkeeping.android.domain.model.feature.schedule.Schedule
import kotlinx.datetime.LocalDate

data class ScheduleModel(
    val state: ScheduleState,
    val scheduleList: List<Schedule>
)
