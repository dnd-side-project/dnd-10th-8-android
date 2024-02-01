package ac.dnd.bookkeeping.android.presentation.common.view.chip

import ac.dnd.bookkeeping.android.domain.model.event.Group
import ac.dnd.bookkeeping.android.domain.model.event.Relation
import ac.dnd.bookkeeping.android.domain.model.event.RelationGroup
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
    onSelectChip: (Group) -> Unit,
    groups : List<Group>
) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        items(groups) { group ->
            ChipItem(
                chipType = chipType,
                currentSelectedId = currentSelectedId,
                chipId = group.id,
                chipText = group.name,
                chipCount = group.relations.size,
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
            Group(
                0,
                "전체",
                relations = listOf(
                    Relation(
                        id = 3679,
                        name = "Jerome Pitts",
                        group = RelationGroup(
                            id = 6599,
                            name = "Andrea Serrano",
                        ),
                        giveMoney = 4190,
                        takeMoney = 4010
                    )
                )
            ),
            Group(
                1,
                "친구",
                relations = listOf(
                    Relation(
                        id = 3679,
                        name = "Jerome Pitts",
                        group = RelationGroup(
                            id = 6599,
                            name = "Andrea Serrano",
                        ),
                        giveMoney = 4190,
                        takeMoney = 4010
                    )
                )
            ),
            Group(
                2,
                "가족",
                listOf()
            )
        )
    )
}
