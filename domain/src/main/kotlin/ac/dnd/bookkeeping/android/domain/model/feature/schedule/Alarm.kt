package ac.dnd.bookkeeping.android.domain.model.feature.schedule

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

data class Alarm(
    val id: Long,
    val relation: AlarmRelation,
    val day: LocalDate,
    val event: String,
    val repeatType: AlarmRepeatType?,
    val repeatFinish: LocalDate?,
    val alarm: LocalDateTime,
    val time: LocalTime?,
    val link: String,
    val location: String,
    val memo: String
)

data class AlarmRelation(
    val id: Long,
    val name: String,
    val group: AlarmRelationGroup
)

data class AlarmRelationGroup(
    val id: Long,
    val name: String
)
