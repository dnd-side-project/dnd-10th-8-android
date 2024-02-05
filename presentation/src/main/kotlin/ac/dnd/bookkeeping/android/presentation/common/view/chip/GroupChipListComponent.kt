package ac.dnd.bookkeeping.android.presentation.common.view.chip

import ac.dnd.bookkeeping.android.domain.model.feature.group.GroupWithRelationDetail
import ac.dnd.bookkeeping.android.domain.model.feature.relation.RelationDetail
import ac.dnd.bookkeeping.android.domain.model.feature.relation.RelationDetailGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun GroupChipListComponent(
    chipType: ChipType = ChipType.LESS_BORDER,
    currentSelectedId: Long,
    onSelectChip: (GroupWithRelationDetail) -> Unit,
    groups: List<GroupWithRelationDetail>
) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        items(groups) { group ->
            ChipItem(
                chipType = chipType,
                currentSelectedId = setOf(currentSelectedId),
                chipId = group.id,
                chipText = group.name,
                chipCount = group.relationList.size,
                onSelectChip = {
                    onSelectChip(group)
                }
            )
        }
    }
}

@Composable
@Preview(backgroundColor = 0xFFFFFFFF)
fun GroupChipPreview() {
    GroupChipListComponent(
        chipType = ChipType.MAIN,
        currentSelectedId = 0,
        onSelectChip = {},
        groups = listOf(
            GroupWithRelationDetail(
                0,
                "전체",
                relationList = listOf(
                    RelationDetail(
                        id = 3679,
                        name = "Jerome Pitts",
                        group = RelationDetailGroup(
                            id = 6599,
                            name = "Andrea Serrano",
                        ),
                        giveMoney = 4190,
                        takeMoney = 4010
                    )
                )
            ),
            GroupWithRelationDetail(
                1,
                "친구",
                relationList = listOf(
                    RelationDetail(
                        id = 3679,
                        name = "Jerome Pitts",
                        group = RelationDetailGroup(
                            id = 6599,
                            name = "Andrea Serrano",
                        ),
                        giveMoney = 4190,
                        takeMoney = 4010
                    )
                )
            ),
            GroupWithRelationDetail(
                2,
                "가족",
                listOf()
            )
        )
    )
}
