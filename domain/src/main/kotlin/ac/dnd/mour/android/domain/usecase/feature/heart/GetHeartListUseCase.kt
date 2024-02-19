package ac.dnd.mour.android.domain.usecase.feature.heart

import ac.dnd.mour.android.domain.model.feature.heart.Heart
import ac.dnd.mour.android.domain.repository.HeartRepository
import javax.inject.Inject

class GetHeartListUseCase @Inject constructor(
    private val heartRepository: HeartRepository
) {
    suspend operator fun invoke(
        sort: String,
        name: String
    ): Result<List<Heart>> {
        return heartRepository.getHeartList(
            sort = sort,
            name = name
        )
    }
}
