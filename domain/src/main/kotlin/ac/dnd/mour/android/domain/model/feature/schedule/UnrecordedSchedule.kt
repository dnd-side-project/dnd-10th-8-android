package ac.dnd.mour.android.domain.model.feature.schedule

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

data class UnrecordedSchedule(
    val id: Long,
    val relation: UnrecordedScheduleRelation,
    val day: LocalDate,
    val event: String,
    val time: LocalTime?,
    val link: String,
    val location: String
)

data class UnrecordedScheduleRelation(
    val id: Long,
    val name: String,
    val group: UnrecordedScheduleRelationGroup
)

data class UnrecordedScheduleRelationGroup(
    val id: Long,
    val name: String
)
