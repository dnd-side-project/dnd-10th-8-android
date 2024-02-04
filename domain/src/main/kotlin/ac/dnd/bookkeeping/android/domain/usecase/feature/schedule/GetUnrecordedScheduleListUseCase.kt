package ac.dnd.bookkeeping.android.domain.usecase.feature.schedule

import ac.dnd.bookkeeping.android.domain.model.schedule.UnrecordedSchedule
import ac.dnd.bookkeeping.android.domain.repository.ScheduleRepository
import javax.inject.Inject

class GetUnrecordedScheduleListUseCase @Inject constructor(
    private val scheduleRepository: ScheduleRepository
) {
    suspend operator fun invoke(
        name: String
    ): Result<List<UnrecordedSchedule>> {
        return scheduleRepository.getUnrecordedScheduleList(
            name = name
        )
    }
}
