package ac.dnd.mour.android.domain.usecase.feature.heart

import ac.dnd.mour.android.domain.model.feature.heart.RelatedHeart
import ac.dnd.mour.android.domain.repository.HeartRepository
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
