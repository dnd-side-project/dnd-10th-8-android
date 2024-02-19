package ac.dnd.mour.android.domain.usecase.feature.group

import ac.dnd.mour.android.domain.model.feature.group.GroupWithRelationDetail
import ac.dnd.mour.android.domain.model.feature.relation.RelationDetail
import ac.dnd.mour.android.domain.model.feature.relation.RelationDetailGroup
import ac.dnd.mour.android.domain.usecase.feature.heart.GetHeartListUseCase
import javax.inject.Inject

class GetGroupHeartHistoryUseCase @Inject constructor(
    private val getHeartListUseCase: GetHeartListUseCase
) {
    suspend operator fun invoke(): Result<List<GroupWithRelationDetail>> {
        return getHeartListUseCase("", "").map { heartList ->
            heartList.groupBy { heart ->
                heart.relation.group.id
            }.map { (key, heartList) ->
                val groupId = heartList.first().relation.group.id
                val groupName = heartList.first().relation.group.name
                GroupWithRelationDetail(
                    id = groupId,
                    name = groupName,
                    relationList = heartList.map { heart ->
                        RelationDetail(
                            id = heart.relation.id,
                            name = heart.relation.name,
                            group = RelationDetailGroup(
                                id = heart.relation.group.id,
                                name = heart.relation.group.name
                            ),
                            giveMoney = heart.giveHistories.sum(),
                            takeMoney = heart.takeHistories.sum()
                        )
                    }
                )
            }
        }
    }
}
