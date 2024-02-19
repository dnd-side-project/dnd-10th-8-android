package ac.dnd.mour.android.presentation.ui.main.home.schedule.add

import ac.dnd.mour.android.presentation.model.schedule.ScheduleAlarmType
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

sealed interface ScheduleAddIntent {
    data class OnConfirm(
        val relationId: Long,
        val day: LocalDate,
        val event: String,
        val alarm: ScheduleAlarmType,
        val time: LocalTime?,
        val link: String,
        val location: String,
        val memo: String
    ) : ScheduleAddIntent

    data object OnRemove : ScheduleAddIntent
}
