package ac.dnd.bookkeeping.android.data.repository.feature.schedule

import ac.dnd.bookkeeping.android.data.remote.local.SharedPreferencesManager
import ac.dnd.bookkeeping.android.data.remote.network.api.ScheduleApi
import ac.dnd.bookkeeping.android.data.remote.network.util.toDomain
import ac.dnd.bookkeeping.android.domain.model.feature.schedule.Alarm
import ac.dnd.bookkeeping.android.domain.model.feature.schedule.AlarmRepeatType
import ac.dnd.bookkeeping.android.domain.model.feature.schedule.Schedule
import ac.dnd.bookkeeping.android.domain.model.feature.schedule.UnrecordedSchedule
import ac.dnd.bookkeeping.android.domain.repository.ScheduleRepository
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import javax.inject.Inject

class RealScheduleRepository @Inject constructor(
    private val scheduleApi: ScheduleApi,
    private val sharedPreferencesManager: SharedPreferencesManager
) : ScheduleRepository {
    override suspend fun addSchedule(
        relationId: Long,
        day: LocalDate,
        event: String,
        repeatType: AlarmRepeatType?,
        repeatFinish: LocalDate?,
        alarm: LocalDateTime?,
        time: LocalTime?,
        link: String,
        location: String,
        memo: String
    ): Result<Long> {
        return scheduleApi.addSchedule(
            relationId = relationId,
            day = day,
            event = event,
            repeatType = repeatType?.value.orEmpty(),
            repeatFinish = repeatFinish,
            alarm = alarm,
            time = time,
            link = link,
            location = location,
            memo = memo
        ).toDomain()
    }

    override suspend fun editSchedule(
        id: Long,
        day: LocalDate,
        event: String,
        repeatType: AlarmRepeatType?,
        repeatFinish: LocalDate?,
        alarm: LocalDateTime?,
        time: LocalTime?,
        link: String,
        location: String,
        memo: String
    ): Result<Unit> {
        return scheduleApi.editSchedule(
            id = id,
            day = day,
            event = event,
            repeatType = repeatType?.value.orEmpty(),
            repeatFinish = repeatFinish,
            alarm = alarm,
            time = time,
            link = link,
            location = location,
            memo = memo
        )
    }

    override suspend fun deleteSchedule(
        id: Long
    ): Result<Unit> {
        return scheduleApi.deleteSchedule(
            id = id
        )
    }

    override suspend fun getUnrecordedScheduleList(
        name: String
    ): Result<List<UnrecordedSchedule>> {
        return scheduleApi.getUnrecordedScheduleList(
            name = name
        ).toDomain()
    }

    override suspend fun getSchedule(
        id: Long
    ): Result<Schedule> {
        return scheduleApi.getSchedule(
            id = id
        ).toDomain()
    }

    override suspend fun getScheduleList(
        year: Int,
        month: Int
    ): Result<List<Schedule>> {
        return scheduleApi.getScheduleList(
            year = year,
            month = month
        ).toDomain()
    }

    override suspend fun getAlarmList(): Result<List<Alarm>> {
        return scheduleApi.getAlarmList().toDomain()
    }
}
