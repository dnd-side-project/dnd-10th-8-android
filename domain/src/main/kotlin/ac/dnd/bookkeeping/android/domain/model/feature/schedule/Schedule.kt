package ac.dnd.bookkeeping.android.domain.model.feature.schedule

import ac.dnd.bookkeeping.android.domain.model.feature.relation.RelationSimple
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

data class Schedule(
    val id: Long,
    val relation: RelationSimple,
    val day: LocalDate,
    val event: String,
    val repeatType: AlarmRepeatType?,
    val repeatFinish: LocalDate?,
    val alarm: LocalDateTime?,
    val time: LocalTime?,
    val link: String,
    val location: String,
    val memo: String
)
