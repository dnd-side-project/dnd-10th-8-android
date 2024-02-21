package ac.dnd.mour.android.presentation.ui.main.home.history.detail

import ac.dnd.mour.android.domain.model.feature.heart.RelatedHeart
import ac.dnd.mour.android.presentation.R
import ac.dnd.mour.android.presentation.common.theme.Body1
import ac.dnd.mour.android.presentation.common.theme.Body2
import ac.dnd.mour.android.presentation.common.theme.Caption2
import ac.dnd.mour.android.presentation.common.theme.Gray000
import ac.dnd.mour.android.presentation.common.theme.Gray200
import ac.dnd.mour.android.presentation.common.theme.Gray600
import ac.dnd.mour.android.presentation.common.theme.Gray700
import ac.dnd.mour.android.presentation.common.theme.Gray800
import ac.dnd.mour.android.presentation.common.theme.Headline3
import ac.dnd.mour.android.presentation.common.theme.Primary1
import ac.dnd.mour.android.presentation.common.theme.Primary4
import ac.dnd.mour.android.presentation.common.theme.Primary5
import ac.dnd.mour.android.presentation.common.theme.Shapes
import ac.dnd.mour.android.presentation.common.util.expansion.measureTextWidth
import ac.dnd.mour.android.presentation.model.history.HistoryTagType
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.LocalDate
import java.text.DecimalFormat

@Composable
fun HistoryDetailItem(
    relatedHeart: RelatedHeart,
    onClick: (RelatedHeart) -> Unit
) {
    val currentViewWidth = LocalConfiguration.current.screenWidthDp.dp

    Box(
        modifier = Modifier
            .clip(Shapes.medium)
            .background(Gray000)
            .clickable {
                onClick(relatedHeart)
            }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 20.dp,
                    vertical = 16.dp
                )
        ) {
            Column {
                Text(
                    text = "${relatedHeart.day.year}년 " +
                            "${relatedHeart.day.monthNumber}월 " +
                            "${relatedHeart.day.dayOfMonth}일",
                    style = Body1.merge(
                        color = Gray600,
                        fontWeight = FontWeight.Normal
                    ),
                    lineHeight = 28.sp
                )
                Text(
                    text = relatedHeart.event,
                    style = Headline3.merge(
                        color = Gray800,
                        fontWeight = FontWeight.SemiBold
                    ),
                )
                Spacer(modifier = Modifier.height(1.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "${if (relatedHeart.give) "-" else ""}${
                            DecimalFormat("#,###")
                                .format(relatedHeart.money)
                        }",
                        style = Headline3.merge(
                            color = if (relatedHeart.give) Color(0xFF1187D8) else Primary5,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                    Text(
                        text = "원",
                        style = Body1.merge(
                            color = if (relatedHeart.give) Color(0xFF1187D8) else Primary4,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))
                val memeStyle = Body2.merge(
                    color = Gray600,
                    fontWeight = FontWeight.Normal
                )
                if (measureTextWidth(relatedHeart.memo, memeStyle) <= currentViewWidth - 80.dp) {
                    if (relatedHeart.memo.isNotEmpty()) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(R.drawable.ic_board),
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = relatedHeart.memo,
                                style = memeStyle
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(6.dp))
                if (relatedHeart.tags.isEmpty()) Spacer(modifier = Modifier.height(4.dp))
                relatedHeart.tags.chunked(5).forEach { rowTags ->
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        rowTags.forEach { tag ->
                            Box(
                                modifier = Modifier
                                    .background(
                                        color = Gray200,
                                        shape = Shapes.small
                                    )
                                    .padding(horizontal = 8.5.dp)
                            ) {
                                Text(
                                    text = tag,
                                    style = Caption2.merge(
                                        color = Gray700,
                                        fontWeight = FontWeight.Medium
                                    ),
                                    lineHeight = 18.sp
                                )
                            }
                        }
                    }
                }
            }
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .background(
                        color = if (relatedHeart.give) Color(0xFFDBF6FF) else Primary1,
                        shape = Shapes.medium
                    )
                    .padding(
                        horizontal = 8.dp,
                        vertical = 5.dp
                    )
            ) {
                Text(
                    text = if (relatedHeart.give) "보낸 마음" else "받은 마음",
                    style = Body2.merge(
                        color = if (relatedHeart.give) Color(0xFF1187D8) else Primary4,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }
    }
}

@Preview
@Composable
private fun HistoryDetailItem1Preview() {
    HistoryDetailItem(
        relatedHeart = RelatedHeart(
            id = 0,
            give = true,
            money = 10000,
            day = LocalDate(2024, 1, 23),
            event = "생일",
            memo = "메모 내용 한 줄 넘어가면 숨김 처리",
            tags = listOf()
        ),
        onClick = {}
    )
}

@Preview
@Composable
private fun HistoryDetailItem2Preview() {
    HistoryDetailItem(
        relatedHeart = RelatedHeart(
            id = 0,
            give = true,
            money = 10000,
            day = LocalDate(2024, 1, 23),
            event = "생일",
            memo = "메모 내용 한 줄 넘어가면 숨김 처리",
            tags = HistoryTagType.entries.map { it.tagName }
        ),
        onClick = {}
    )
}

@Preview
@Composable
private fun HistoryDetailItem3Preview() {
    HistoryDetailItem(
        relatedHeart = RelatedHeart(
            id = 0,
            give = false,
            money = 500000,
            day = LocalDate(2024, 1, 23),
            event = "생일",
            memo = "메모 내용 한 줄 넘어가면 숨김 처리 메모 내용 한 줄 넘어가면 숨김 처리 ",
            tags = listOf("참석", "현금")
        ),
        onClick = {}
    )
}
