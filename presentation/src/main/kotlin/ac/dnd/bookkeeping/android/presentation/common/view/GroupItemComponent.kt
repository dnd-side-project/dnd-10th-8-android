package ac.dnd.bookkeeping.android.presentation.common.view

import ac.dnd.bookkeeping.android.domain.model.event.Group
import ac.dnd.bookkeeping.android.domain.model.event.Relation
import ac.dnd.bookkeeping.android.domain.model.event.RelationGroup
import ac.dnd.bookkeeping.android.presentation.common.theme.Body1
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray000
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray600
import ac.dnd.bookkeeping.android.presentation.common.theme.Primary1
import ac.dnd.bookkeeping.android.presentation.common.theme.Primary4
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun GroupItemComponent(
    isViewNumbering: Boolean,
    selectedId: Long,
    onSelectChip: (Group) -> Unit,
    groups: List<Group>
) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        items(groups) { group ->
            GroupItemChip(
                isViewNumbering = isViewNumbering,
                selectedId = selectedId,
                group = group,
                onSelectChip = {
                    onSelectChip(group)
                }
            )
        }
    }
}

@Composable
fun GroupItemChip(
    isViewNumbering: Boolean,
    selectedId: Long,
    group: Group,
    onSelectChip: (Group) -> Unit
) {
    val isSelected = selectedId == group.id
    val groupCount = group.relations.size
    val backgroundColor = animateColorAsState(
        targetValue = if (isSelected) Primary1 else Gray000,
        label = "background"
    )
    val textColor = animateColorAsState(
        targetValue = if (isSelected) Primary4 else Gray600,
        label = "textColor"
    )
    val borderColor = animateColorAsState(
        targetValue = if (isSelected) Primary4 else Color.Transparent,
        label = "borderColor"
    )

    Row(
        modifier = Modifier
            .background(
                color = backgroundColor.value,
                shape = RoundedCornerShape(100.dp)
            )
            .border(
                color = borderColor.value,
                width = 1.dp,
                shape = RoundedCornerShape(100.dp)
            )
            .padding(
                horizontal = 14.dp,
                vertical = 6.5.dp
            )
            .clickable {
                onSelectChip(group)
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = group.name,
            style = Body1.merge(
                color = textColor.value
            )
        )
        if (groupCount > 0 && isViewNumbering) {
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = groupCount.toString(),
                style = Body1.merge(
                    color = textColor.value
                )
            )
        }
    }
}

@Composable
@Preview(backgroundColor = 0xFFFFFFFF)
fun GroupChipPreview() {
    GroupItemComponent(
        isViewNumbering = true,
        selectedId = 0,
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
