package ac.dnd.bookkeeping.android.domain.model.feature.schedule

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

data class Schedule(
    val id: Long,
    val relation: ScheduleRelation,
    val day: LocalDate,
    val event: String,
    val time: LocalTime?,
    val link: String,
    val location: String,
    val memo: String
)

data class ScheduleRelation(
    val id: Long,
    val name: String,
    val group: ScheduleRelationGroup
)

data class ScheduleRelationGroup(
    val id: Long,
    val name: String
)
