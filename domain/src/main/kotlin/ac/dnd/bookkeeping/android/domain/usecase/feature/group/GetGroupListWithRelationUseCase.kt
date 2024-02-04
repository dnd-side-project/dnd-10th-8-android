package ac.dnd.bookkeeping.android.domain.usecase.feature.group

import ac.dnd.bookkeeping.android.domain.model.group.GroupWithRelation
import ac.dnd.bookkeeping.android.domain.usecase.feature.relation.GetRelationListUseCase
import javax.inject.Inject

class GetGroupListWithRelationUseCase @Inject constructor(
    private val getRelationListUseCase: GetRelationListUseCase
) {
    suspend operator fun invoke(): Result<List<GroupWithRelation>> {
        return getRelationListUseCase("").map { relationList ->
            relationList.groupBy {
                it.group.id
            }.map { (_, relationList) ->
                val group = relationList.first().group
                GroupWithRelation(
                    id = group.id,
                    name = group.name,
                    relationList = relationList
                )
            }
        }
    }
}
