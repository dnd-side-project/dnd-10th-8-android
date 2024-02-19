package ac.dnd.mour.android.domain.usecase.feature.schedule

import ac.dnd.mour.android.domain.model.feature.schedule.Schedule
import ac.dnd.mour.android.domain.repository.ScheduleRepository
import javax.inject.Inject

class GetScheduleListUseCase @Inject constructor(
    private val scheduleRepository: ScheduleRepository
) {
    suspend operator fun invoke(
        year: Int,
        month: Int
    ): Result<List<Schedule>> {
        return scheduleRepository.getScheduleList(
            year = year,
            month = month
        )
    }
}
