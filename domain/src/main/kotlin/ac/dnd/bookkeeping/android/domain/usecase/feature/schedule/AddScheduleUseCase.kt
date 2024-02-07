package ac.dnd.bookkeeping.android.domain.usecase.feature.schedule

import ac.dnd.bookkeeping.android.domain.model.feature.schedule.AlarmRepeatType
import ac.dnd.bookkeeping.android.domain.repository.ScheduleRepository
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import javax.inject.Inject

class AddScheduleUseCase @Inject constructor(
    private val scheduleRepository: ScheduleRepository
) {
    suspend operator fun invoke(
        relationId: Long,
        day: LocalDate,
        event: String,
        repeatType: AlarmRepeatType,
        repeatFinish: LocalDate,
        alarm: LocalDateTime,
        time: LocalTime,
        link: String,
        location: String,
        memo: String
    ): Result<Long> {
        return scheduleRepository.addSchedule(
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
}
