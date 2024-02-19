package ac.dnd.mour.android.domain.usecase.feature.schedule

import ac.dnd.mour.android.domain.model.feature.schedule.Alarm
import ac.dnd.mour.android.domain.repository.ScheduleRepository
import javax.inject.Inject

class GetAlarmListUseCase @Inject constructor(
    private val scheduleRepository: ScheduleRepository
) {
    suspend operator fun invoke(): Result<List<Alarm>> {
        return scheduleRepository.getAlarmList()
    }
}
