package ac.dnd.mour.android.data.repository.feature.schedule

import ac.dnd.mour.android.domain.model.feature.relation.RelationSimple
import ac.dnd.mour.android.domain.model.feature.relation.RelationSimpleGroup
import ac.dnd.mour.android.domain.model.feature.schedule.Alarm
import ac.dnd.mour.android.domain.model.feature.schedule.AlarmRelation
import ac.dnd.mour.android.domain.model.feature.schedule.AlarmRelationGroup
import ac.dnd.mour.android.domain.model.feature.schedule.AlarmRepeatType
import ac.dnd.mour.android.domain.model.feature.schedule.Schedule
import ac.dnd.mour.android.domain.model.feature.schedule.UnrecordedSchedule
import ac.dnd.mour.android.domain.model.feature.schedule.UnrecordedScheduleRelation
import ac.dnd.mour.android.domain.model.feature.schedule.UnrecordedScheduleRelationGroup
import ac.dnd.mour.android.domain.repository.ScheduleRepository
import kotlinx.coroutines.delay
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.number
import kotlinx.datetime.todayIn
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

    override suspend fun getSchedule(
        id: Long
    ): Result<Schedule> {
        randomShortDelay()
        return Result.success(
            Schedule(
                id = 2378,
                relation = RelationSimple(
                    id = 4601,
                    name = "이다빈",
                    group = RelationSimpleGroup(
                        id = 5690,
                        name = "친구"
                    )
                ),
                day = LocalDate(2024, 2, 25),
                event = "결혼",
                repeatType = null,
                repeatFinish = null,
                alarm = LocalDateTime(2024, 2, 25, 12, 0),
                time = LocalTime(12, 0),
                link = "https://www.google.com/",
                location = "롯데월드 호텔",
                memo = "메모입니다."
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
                    relation = RelationSimple(
                        id = 4601,
                        name = "이다빈",
                        group = RelationSimpleGroup(
                            id = 5690,
                            name = "친구"
                        )
                    ),
                    day = LocalDate(2024, 2, 25),
                    event = "결혼",
                    repeatType = null,
                    repeatFinish = null,
                    alarm = LocalDateTime(2024, 2, 25, 12, 0),
                    time = LocalTime(12, 0),
                    link = "https://www.google.com/",
                    location = "롯데월드 호텔",
                    memo = "메모입니다."
                ),
                Schedule(
                    id = 23278,
                    relation = RelationSimple(
                        id = 46201,
                        name = "김진우",
                        group = RelationSimpleGroup(
                            id = 56290,
                            name = "가족"
                        )
                    ),
                    day = LocalDate(2024, 2, 25),
                    event = "생일",
                    repeatType = null,
                    repeatFinish = null,
                    alarm = LocalDateTime(2024, 2, 25, 12, 0),
                    time = null,
                    link = "",
                    location = "",
                    memo = ""
                )
            )
        )
    }

    override suspend fun getAlarmList(): Result<List<Alarm>> {
        randomShortDelay()
        val now = Clock.System.todayIn(TimeZone.currentSystemDefault())
        val date1 = now.minus(1, DateTimeUnit.DAY)
        val date2 = now.minus(10, DateTimeUnit.DAY)
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
                    day = date1,
                    event = "doctus",
                    repeatType = null,
                    repeatFinish = null,
                    alarm = LocalDateTime(date1.year, date1.month.number, date1.dayOfMonth, 9, 0),
                    time = null,
                    link = "urna",
                    location = "quas",
                    memo = "error"
                ),
                Alarm(
                    id = 98319,
                    relation = AlarmRelation(
                        id = 81211,
                        name = "Seth Sears2",
                        group = AlarmRelationGroup(
                            id = 98154,
                            name = "Pete Lambert2"
                        )
                    ),
                    day = date2,
                    event = "결혼",
                    repeatType = null,
                    repeatFinish = null,
                    alarm = LocalDateTime(date2.year, date2.month.number, date2.dayOfMonth, 12, 0),
                    time = LocalTime(14, 0),
                    link = "",
                    location = "",
                    memo = ""
                ),
                Alarm(
                    id = 983129,
                    relation = AlarmRelation(
                        id = 812211,
                        name = "Seth Sears3",
                        group = AlarmRelationGroup(
                            id = 981254,
                            name = "Pete Lambert3"
                        )
                    ),
                    day = LocalDate(2024, 2, 25),
                    event = "결혼",
                    repeatType = null,
                    repeatFinish = null,
                    alarm = LocalDateTime(2024, 2, 25, 12, 0),
                    time = LocalTime(14, 0),
                    link = "",
                    location = "",
                    memo = ""
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
