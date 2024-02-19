package ac.dnd.mour.android.domain.usecase.feature.group

import ac.dnd.mour.android.domain.model.feature.group.GroupWithRelationSimple
import ac.dnd.mour.android.domain.usecase.feature.relation.GetRelationListUseCase
import javax.inject.Inject

class GetGroupListWithRelationSimpleUseCase @Inject constructor(
    private val getRelationListUseCase: GetRelationListUseCase
) {
    suspend operator fun invoke(): Result<List<GroupWithRelationSimple>> {
        return getRelationListUseCase("").map { relationList ->
            relationList.groupBy {
                it.group.id
            }.map { (_, relationList) ->
                val group = relationList.first().group
                GroupWithRelationSimple(
                    id = group.id,
                    name = group.name,
                    relationList = relationList
                )
            }
        }
    }
}
