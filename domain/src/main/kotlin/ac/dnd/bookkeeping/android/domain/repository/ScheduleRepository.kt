package ac.dnd.bookkeeping.android.domain.repository

import ac.dnd.bookkeeping.android.domain.model.schedule.UnrecordedSchedule
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

interface ScheduleRepository {

    suspend fun addSchedule(
        relationId: Long,
        day: LocalDate,
        event: String,
        repeatType: String,
        repeatFinish: LocalDate,
        alarm: LocalDateTime,
        time: LocalTime,
        link: String,
        location: String,
        memo: String = "",
    ): Result<Long>

    suspend fun editSchedule(
        id: Long,
        relationId: Long,
        day: LocalDate,
        event: String,
        repeatType: String,
        repeatFinish: LocalDate,
        alarm: LocalDateTime,
        time: LocalTime,
        link: String,
        location: String,
        memo: String = "",
    ): Result<Unit>

    suspend fun deleteSchedule(
        id: Long,
    ): Result<Unit>

    suspend fun getUnrecordedScheduleList(
        name: String = ""
    ): Result<List<UnrecordedSchedule>>
}
