package ac.dnd.mour.android.presentation.common.view.chip

import ac.dnd.mour.android.presentation.common.theme.Body1
import ac.dnd.mour.android.presentation.common.theme.Gray000
import ac.dnd.mour.android.presentation.common.theme.Gray500
import ac.dnd.mour.android.presentation.common.theme.Gray700
import ac.dnd.mour.android.presentation.common.theme.Gray800
import ac.dnd.mour.android.presentation.common.theme.Primary1
import ac.dnd.mour.android.presentation.common.theme.Primary4
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ChipItem(
    chipType: ChipType = ChipType.LESS_BORDER,
    currentSelectedId: Set<Long>,
    chipId: Long,
    chipText: String,
    chipCount: Int = 0,
    onSelectChip: (chipId: Long) -> Unit
) {
    val isSelected = chipId in currentSelectedId
    val backgroundColor = animateColorAsState(
        targetValue = when (chipType) {
            ChipType.MAIN -> if (isSelected) Gray800 else Gray000
            else -> if (isSelected) Primary1 else Gray000
        },
        label = "background"
    )
    val textColor = animateColorAsState(
        targetValue = when (chipType) {
            ChipType.MAIN -> if (isSelected) Gray000 else Gray700
            else -> if (isSelected) Primary4 else Gray700
        },
        label = "textColor"
    )
    val borderColor = animateColorAsState(
        targetValue = when (chipType) {
            ChipType.BORDER -> if (isSelected) Primary4 else Gray500
            else -> Color.Transparent
        },
        label = "borderColor"
    )

    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(100.dp))
            .background(
                color = backgroundColor.value,
                shape = RoundedCornerShape(100.dp)
            )
            .border(
                color = borderColor.value,
                width = 1.dp,
                shape = RoundedCornerShape(100.dp)
            )
            .clickable {
                onSelectChip(chipId)
            }
            .padding(
                horizontal = 14.dp,
                vertical = 6.5.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = chipText,
            style = Body1.merge(color = textColor.value),
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
        )
        if (chipCount > 0) {
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = chipCount.toString(),
                style = Body1.merge(color = textColor.value)
            )
        }
    }
}

@Preview(backgroundColor = 0xFFF6F6F7, showBackground = true)
@Composable
fun ChipsType1Preview() {
    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        ChipItem(
            chipType = ChipType.LESS_BORDER,
            currentSelectedId = setOf(0),
            chipId = 0,
            chipText = "가족",
            chipCount = 5,
            onSelectChip = {}
        )
        ChipItem(
            chipType = ChipType.LESS_BORDER,
            currentSelectedId = setOf(0),
            chipId = 1,
            chipText = "친구",
            chipCount = 0,
            onSelectChip = {}
        )
    }
}

@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
@Composable
fun ChipsType2Preview() {
    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        ChipItem(
            chipType = ChipType.BORDER,
            currentSelectedId = setOf(0),
            chipId = 0,
            chipText = "가족",
            chipCount = 5,
            onSelectChip = {}
        )
        ChipItem(
            chipType = ChipType.BORDER,
            currentSelectedId = setOf(0),
            chipId = 1,
            chipText = "친구",
            chipCount = 0,
            onSelectChip = {}
        )
    }
}

@Preview(backgroundColor = 0xFFF6F6F7, showBackground = true)
@Composable
fun ChipsType3Preview() {
    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        ChipItem(
            chipType = ChipType.MAIN,
            currentSelectedId = setOf(0),
            chipId = 0,
            chipText = "가족",
            chipCount = 5,
            onSelectChip = {}
        )
        ChipItem(
            chipType = ChipType.MAIN,
            currentSelectedId = setOf(0),
            chipId = 1,
            chipText = "친구",
            chipCount = 0,
            onSelectChip = {}
        )
    }
}
