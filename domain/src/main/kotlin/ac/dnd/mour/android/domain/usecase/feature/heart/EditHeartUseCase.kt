package ac.dnd.mour.android.domain.usecase.feature.heart

import ac.dnd.mour.android.domain.repository.HeartRepository
import javax.inject.Inject
import kotlinx.datetime.LocalDate

class EditHeartUseCase @Inject constructor(
    private val heartRepository: HeartRepository
) {
    suspend operator fun invoke(
        id: Long,
        money: Long,
        day: LocalDate,
        event: String,
        memo: String,
        tags: List<String>
    ): Result<Unit> {
        return heartRepository.editHeart(
            id = id,
            money = money,
            day = day,
            event = event,
            memo = memo,
            tags = tags
        )
    }
}
