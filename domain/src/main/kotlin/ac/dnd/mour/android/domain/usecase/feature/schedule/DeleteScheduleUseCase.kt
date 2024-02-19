package ac.dnd.mour.android.domain.usecase.feature.schedule

import ac.dnd.mour.android.domain.repository.ScheduleRepository
import javax.inject.Inject

class DeleteScheduleUseCase @Inject constructor(
    private val scheduleRepository: ScheduleRepository
) {
    suspend operator fun invoke(
        id: Long
    ): Result<Unit> {
        return scheduleRepository.deleteSchedule(
            id = id
        )
    }
}
