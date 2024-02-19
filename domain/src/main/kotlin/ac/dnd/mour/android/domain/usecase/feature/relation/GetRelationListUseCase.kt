package ac.dnd.mour.android.domain.usecase.feature.relation

import ac.dnd.mour.android.domain.model.feature.relation.RelationSimple
import ac.dnd.mour.android.domain.repository.RelationRepository
import javax.inject.Inject

class GetRelationListUseCase @Inject constructor(
    private val relationRepository: RelationRepository
) {
    suspend operator fun invoke(
        name: String
    ): Result<List<RelationSimple>> {
        return relationRepository.getRelationList(
            name = name
        )
    }
}
