package ac.dnd.bookkeeping.android.domain.usecase.feature.heart

import ac.dnd.bookkeeping.android.domain.model.heart.RelatedHeart
import ac.dnd.bookkeeping.android.domain.repository.HeartRepository
import javax.inject.Inject

class GetRelatedHeartListUseCase @Inject constructor(
    private val heartRepository: HeartRepository
) {
    suspend operator fun invoke(
        id: Long,
        sort: String
    ): Result<List<RelatedHeart>> {
        return heartRepository.getRelatedHeartList(
            id = id,
            sort = sort
        )
    }
}
