package ac.dnd.bookkeeping.android.domain.usecase.feature.schedule

import ac.dnd.bookkeeping.android.domain.model.feature.schedule.Schedule
import ac.dnd.bookkeeping.android.domain.repository.ScheduleRepository
import javax.inject.Inject

class GetScheduleUseCase @Inject constructor(
    private val scheduleRepository: ScheduleRepository
) {
    suspend operator fun invoke(
        id: Long
    ): Result<Schedule> {
        return scheduleRepository.getSchedule(
            id = id
        )
    }
}
