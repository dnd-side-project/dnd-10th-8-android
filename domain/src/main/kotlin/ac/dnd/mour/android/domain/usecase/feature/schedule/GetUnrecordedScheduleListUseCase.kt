package ac.dnd.mour.android.domain.usecase.feature.schedule

import ac.dnd.mour.android.domain.model.feature.schedule.UnrecordedSchedule
import ac.dnd.mour.android.domain.repository.ScheduleRepository
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
