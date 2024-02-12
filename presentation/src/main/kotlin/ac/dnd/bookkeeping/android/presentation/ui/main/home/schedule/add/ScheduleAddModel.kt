package ac.dnd.bookkeeping.android.presentation.ui.main.home.schedule.add

import ac.dnd.bookkeeping.android.domain.model.feature.schedule.Schedule

data class ScheduleAddModel(
    val state: ScheduleAddState,
    val schedule: Schedule?
)
