package ac.dnd.bookkeeping.android.presentation.common.view.chip

import ac.dnd.bookkeeping.android.domain.model.legacy.GroupLegacy
import ac.dnd.bookkeeping.android.domain.model.legacy.RelationLegacy
import ac.dnd.bookkeeping.android.domain.model.legacy.RelationGroupLegacy
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
    onSelectChip: (GroupLegacy) -> Unit,
    groups : List<GroupLegacy>
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
            GroupLegacy(
                0,
                "전체",
                relations = listOf(
                    RelationLegacy(
                        id = 3679,
                        name = "Jerome Pitts",
                        group = RelationGroupLegacy(
                            id = 6599,
                            name = "Andrea Serrano",
                        ),
                        giveMoney = 4190,
                        takeMoney = 4010
                    )
                )
            ),
            GroupLegacy(
                1,
                "친구",
                relations = listOf(
                    RelationLegacy(
                        id = 3679,
                        name = "Jerome Pitts",
                        group = RelationGroupLegacy(
                            id = 6599,
                            name = "Andrea Serrano",
                        ),
                        giveMoney = 4190,
                        takeMoney = 4010
                    )
                )
            ),
            GroupLegacy(
                2,
                "가족",
                listOf()
            )
        )
    )
}
