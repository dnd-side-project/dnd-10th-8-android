package ac.dnd.bookkeeping.android.data.repository.schedule

import ac.dnd.bookkeeping.android.data.remote.local.SharedPreferencesManager
import ac.dnd.bookkeeping.android.data.remote.network.api.ScheduleApi
import ac.dnd.bookkeeping.android.data.remote.network.util.toDomain
import ac.dnd.bookkeeping.android.domain.model.group.Group
import ac.dnd.bookkeeping.android.domain.model.schedule.UnrecordedSchedule
import ac.dnd.bookkeeping.android.domain.repository.GroupRepository
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
        repeatType: String,
        repeatFinish: LocalDate,
        alarm: LocalDateTime,
        time: LocalTime,
        link: String,
        location: String,
        memo: String
    ): Result<Long> {
        return scheduleApi.addSchedule(
            relationId = relationId,
            day = day,
            event = event,
            repeatType = repeatType,
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
        relationId: Long,
        day: LocalDate,
        event: String,
        repeatType: String,
        repeatFinish: LocalDate,
        alarm: LocalDateTime,
        time: LocalTime,
        link: String,
        location: String,
        memo: String
    ): Result<Unit> {
        return scheduleApi.editSchedule(
            id = id,
            relationId = relationId,
            day = day,
            event = event,
            repeatType = repeatType,
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
}
