package ac.dnd.mour.android.domain.usecase.feature.heart

import ac.dnd.mour.android.domain.repository.HeartRepository
import javax.inject.Inject
import kotlinx.datetime.LocalDate

class AddHeartUseCase @Inject constructor(
    private val heartRepository: HeartRepository
) {
    suspend operator fun invoke(
        relationId: Long,
        give: Boolean,
        money: Long,
        day: LocalDate,
        event: String,
        memo: String,
        tags: List<String>
    ): Result<Long> {
        return heartRepository.addHeart(
            relationId = relationId,
            give = give,
            money = money,
            day = day,
            event = event,
            memo = memo,
            tags = tags
        )
    }
}
