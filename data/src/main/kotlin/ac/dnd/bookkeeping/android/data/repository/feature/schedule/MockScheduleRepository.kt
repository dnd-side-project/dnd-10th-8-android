package ac.dnd.bookkeeping.android.data.repository.feature.schedule

import ac.dnd.bookkeeping.android.domain.model.feature.schedule.UnrecordedSchedule
import ac.dnd.bookkeeping.android.domain.model.feature.schedule.UnrecordedScheduleRelation
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
        repeatType: String,
        repeatFinish: LocalDate,
        alarm: LocalDateTime,
        time: LocalTime,
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
        repeatType: String,
        repeatFinish: LocalDate,
        alarm: LocalDateTime,
        time: LocalTime,
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
                        name = "Alphonse Berg"
                    ),
                    day = LocalDate(2024, 2, 25),
                    event = "velit",
                    alarm = LocalDateTime(2024, 2, 25, 8, 0),
                    time = LocalTime(8, 0),
                    link = "maximus",
                    location = "noster"
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
