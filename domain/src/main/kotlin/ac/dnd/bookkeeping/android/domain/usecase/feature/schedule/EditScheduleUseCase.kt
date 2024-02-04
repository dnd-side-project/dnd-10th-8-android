package ac.dnd.bookkeeping.android.domain.usecase.feature.schedule

import ac.dnd.bookkeeping.android.domain.repository.ScheduleRepository
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import javax.inject.Inject

class EditScheduleUseCase @Inject constructor(
    private val scheduleRepository: ScheduleRepository
) {
    suspend operator fun invoke(
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
        return scheduleRepository.editSchedule(
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
}
