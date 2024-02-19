package ac.dnd.mour.android.domain.repository

import ac.dnd.mour.android.domain.model.feature.schedule.Alarm
import ac.dnd.mour.android.domain.model.feature.schedule.AlarmRepeatType
import ac.dnd.mour.android.domain.model.feature.schedule.Schedule
import ac.dnd.mour.android.domain.model.feature.schedule.UnrecordedSchedule
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

interface ScheduleRepository {

    suspend fun addSchedule(
        relationId: Long,
        day: LocalDate,
        event: String,
        repeatType: AlarmRepeatType?,
        repeatFinish: LocalDate?,
        alarm: LocalDateTime?,
        time: LocalTime?,
        link: String,
        location: String,
        memo: String,
    ): Result<Long>

    suspend fun editSchedule(
        id: Long,
        day: LocalDate,
        event: String,
        repeatType: AlarmRepeatType?,
        repeatFinish: LocalDate?,
        alarm: LocalDateTime?,
        time: LocalTime?,
        link: String,
        location: String,
        memo: String,
    ): Result<Unit>

    suspend fun deleteSchedule(
        id: Long,
    ): Result<Unit>

    suspend fun getUnrecordedScheduleList(
        name: String
    ): Result<List<UnrecordedSchedule>>

    suspend fun getSchedule(
        id: Long
    ): Result<Schedule>

    suspend fun getScheduleList(
        year: Int,
        month: Int
    ): Result<List<Schedule>>

    suspend fun getAlarmList(): Result<List<Alarm>>
}
