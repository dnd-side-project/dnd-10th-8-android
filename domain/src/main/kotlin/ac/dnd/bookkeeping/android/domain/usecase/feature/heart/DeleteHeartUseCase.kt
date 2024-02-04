package ac.dnd.bookkeeping.android.domain.usecase.feature.heart

import ac.dnd.bookkeeping.android.domain.repository.HeartRepository
import javax.inject.Inject

class DeleteHeartUseCase @Inject constructor(
    private val heartRepository: HeartRepository
) {
    suspend operator fun invoke(
        id: Long,
    ): Result<Unit> {
        return heartRepository.deleteHeart(
            id = id
        )
    }
}
