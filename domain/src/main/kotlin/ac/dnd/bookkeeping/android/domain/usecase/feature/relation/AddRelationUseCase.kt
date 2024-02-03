package ac.dnd.bookkeeping.android.domain.usecase.feature.relation

import ac.dnd.bookkeeping.android.domain.repository.RelationRepository
import javax.inject.Inject

class AddRelationUseCase @Inject constructor(
    private val relationRepository: RelationRepository
) {
    suspend operator fun invoke(
        groupId: Long,
        name: String,
        imageUrl: String,
        memo: String
    ): Result<Long> {
        return relationRepository.addRelation(
            groupId = groupId,
            name = name,
            imageUrl = imageUrl,
            memo = memo
        )
    }
}
