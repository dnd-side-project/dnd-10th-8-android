package ac.dnd.mour.android.domain.usecase.feature.heart

import ac.dnd.mour.android.domain.repository.HeartRepository
import javax.inject.Inject

class AddUnrecordedHeartUseCase @Inject constructor(
    private val heartRepository: HeartRepository
) {
    suspend operator fun invoke(
        scheduleId: Long,
        money: Long,
        tags: List<String>
    ): Result<Long> {
        return heartRepository.addUnrecordedHeart(
            scheduleId = scheduleId,
            money = money,
            tags = tags
        )
    }
}
