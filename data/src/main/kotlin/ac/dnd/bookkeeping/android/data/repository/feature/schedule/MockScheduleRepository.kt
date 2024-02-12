package ac.dnd.bookkeeping.android.data.repository.feature.schedule

import ac.dnd.bookkeeping.android.domain.model.feature.schedule.Alarm
import ac.dnd.bookkeeping.android.domain.model.feature.schedule.AlarmRelation
import ac.dnd.bookkeeping.android.domain.model.feature.schedule.AlarmRelationGroup
import ac.dnd.bookkeeping.android.domain.model.feature.schedule.AlarmRepeatType
import ac.dnd.bookkeeping.android.domain.model.feature.schedule.Schedule
import ac.dnd.bookkeeping.android.domain.model.feature.schedule.ScheduleRelation
import ac.dnd.bookkeeping.android.domain.model.feature.schedule.ScheduleRelationGroup
import ac.dnd.bookkeeping.android.domain.model.feature.schedule.UnrecordedSchedule
import ac.dnd.bookkeeping.android.domain.model.feature.schedule.UnrecordedScheduleRelation
import ac.dnd.bookkeeping.android.domain.model.feature.schedule.UnrecordedScheduleRelationGroup
import ac.dnd.bookkeeping.android.domain.repository.ScheduleRepository
import kotlinx.coroutines.delay
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import javax.inject.Inject

class MockScheduleRepository @Inject constructor() : ScheduleRepository {

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
        randomShortDelay()
        return Result.success(-1)
    }

    override suspend fun editSchedule(
        id: Long,
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
    ): Result<Unit> {
        randomShortDelay()
        return Result.success(Unit)
    }

    override suspend fun deleteSchedule(
        id: Long
    ): Result<Unit> {
        randomShortDelay()
        return Result.success(Unit)
    }

    override suspend fun getUnrecordedScheduleList(
        name: String
    ): Result<List<UnrecordedSchedule>> {
        randomLongDelay()
        return Result.success(
            listOf(
                UnrecordedSchedule(
                    id = 2655,
                    relation = UnrecordedScheduleRelation(
                        id = 9178,
                        name = "Alphonse Berg",
                        group = UnrecordedScheduleRelationGroup(
                            id = 1,
                            name = "Dolores"
                        )
                    ),
                    day = LocalDate(2024, 2, 25),
                    event = "velit",
                    time = LocalTime(8, 0),
                    link = "maximus",
                    location = "noster"
                )
            )
        )
    }

    override suspend fun getScheduleList(
        year: Int,
        month: Int
    ): Result<List<Schedule>> {
        randomShortDelay()
        return Result.success(
            listOf(
                Schedule(
                    id = 2378,
                    relation = ScheduleRelation(
                        id = 4601,
                        name = "이다빈",
                        group = ScheduleRelationGroup(
                            id = 5690,
                            name = "친구"
                        )
                    ),
                    day = LocalDate(2024, 2, 25),
                    event = "결혼",
                    time = LocalTime(12, 0),
                    link = "https://www.google.com/",
                    location = "롯데월드 호텔"
                ),
                Schedule(
                    id = 23278,
                    relation = ScheduleRelation(
                        id = 46201,
                        name = "김진우",
                        group = ScheduleRelationGroup(
                            id = 56290,
                            name = "가족"
                        )
                    ),
                    day = LocalDate(2024, 2, 25),
                    event = "생일",
                    time = null,
                    link = "",
                    location = ""
                )
            )
        )
    }

    override suspend fun getAlarmList(): Result<List<Alarm>> {
        randomShortDelay()
        return Result.success(
            listOf(
                Alarm(
                    id = 9839,
                    relation = AlarmRelation(
                        id = 8121,
                        name = "Seth Sears",
                        group = AlarmRelationGroup(
                            id = 9854,
                            name = "Pete Lambert"
                        )
                    ),
                    day = LocalDate(2024, 2, 25),
                    event = "doctus",
                    repeatType = null,
                    repeatFinish = null,
                    alarm = LocalDateTime(2024, 2, 25, 9, 0),
                    time = null,
                    link = "urna",
                    location = "quas",
                    memo = "error"
                )
            )
        )
    }

    private suspend fun randomShortDelay() {
        delay(LongRange(100, 500).random())
    }

    private suspend fun randomLongDelay() {
        delay(LongRange(500, 2000).random())
    }
}
