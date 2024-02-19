package ac.dnd.mour.android.presentation.ui.main.home.schedule

import ac.dnd.mour.android.domain.model.feature.schedule.Schedule

data class ScheduleModel(
    val state: ScheduleState,
    val scheduleList: List<Schedule>
)
