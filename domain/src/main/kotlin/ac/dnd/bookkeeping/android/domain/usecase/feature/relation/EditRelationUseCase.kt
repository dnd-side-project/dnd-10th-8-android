package ac.dnd.bookkeeping.android.domain.usecase.feature.relation

import ac.dnd.bookkeeping.android.domain.repository.RelationRepository
import javax.inject.Inject

class EditRelationUseCase @Inject constructor(
    private val relationRepository: RelationRepository
) {
    suspend operator fun invoke(
        id: Long,
        groupId: Long,
        name: String,
        imageUrl: String,
        memo: String
    ): Result<Unit> {
        return relationRepository.editRelation(
            id = id,
            groupId = groupId,
            name = name,
            imageUrl = imageUrl,
            memo = memo
        )
    }
}
