package ac.dnd.mour.android.domain.usecase.feature.relation

import ac.dnd.mour.android.domain.model.feature.relation.RelationDetailWithUserInfo
import ac.dnd.mour.android.domain.repository.RelationRepository
import javax.inject.Inject

class GetRelationUseCase @Inject constructor(
    private val relationRepository: RelationRepository
) {
    suspend operator fun invoke(
        id: Long
    ): Result<RelationDetailWithUserInfo> {
        return relationRepository.getRelation(
            id = id
        )
    }
}
