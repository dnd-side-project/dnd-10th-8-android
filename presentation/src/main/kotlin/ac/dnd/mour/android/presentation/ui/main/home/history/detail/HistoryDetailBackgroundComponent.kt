package ac.dnd.mour.android.presentation.ui.main.home.history.detail

import ac.dnd.mour.android.domain.model.feature.heart.RelatedHeart
import ac.dnd.mour.android.domain.model.feature.relation.RelationDetailGroup
import ac.dnd.mour.android.domain.model.feature.relation.RelationDetailWithUserInfo
import ac.dnd.mour.android.presentation.R
import ac.dnd.mour.android.presentation.common.theme.Body1
import ac.dnd.mour.android.presentation.common.theme.Body2
import ac.dnd.mour.android.presentation.common.theme.Gray000
import ac.dnd.mour.android.presentation.common.theme.Gray100
import ac.dnd.mour.android.presentation.common.theme.Headline0
import ac.dnd.mour.android.presentation.common.theme.Headline3
import ac.dnd.mour.android.presentation.model.history.HistoryDetailGrowthType
import ac.dnd.mour.android.presentation.model.history.HistoryTagType
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalDate

@Composable
fun HistoryDetailBackgroundComponent(
    currentGrowthType: HistoryDetailGrowthType,
    model: HistoryDetailModel,
    contentHeight: Dp,
    onClickRelationInfo: () -> Unit,
    onClickGrowthInfo: () -> Unit,
    onClickBack: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(contentHeight)
            .background(color = Color(currentGrowthType.backgroundColor))
    ) {

        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(
                    start = 22.dp,
                    bottom = 71.dp
                )
        ) {
            Image(
                painter = painterResource(R.drawable.ic_info),
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
                    .clickable {
                        onClickGrowthInfo()
                    }
            )
        }

        Box(
            modifier = Modifier
                .padding(
                    top = 54.dp,
                )
                .offset(x = 19.dp)
                .align(Alignment.TopEnd)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_star),
                modifier = Modifier
                    .size(118.dp),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color(0x26FFFFFF))
            )
        }

        Box(
            modifier = Modifier
                .padding(
                    bottom = 133.dp
                )
                .offset(x = (-43).dp)
                .align(Alignment.BottomStart)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_star),
                modifier = Modifier
                    .size(118.dp),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color(0x26FFFFFF))
            )
        }

        //TODO Lottie
        Box(
            modifier = Modifier.align(Alignment.BottomEnd)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Box(modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(R.drawable.ic_chevron_left),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Gray000),
                    modifier = Modifier
                        .clickable {
                            onClickBack()
                        }
                        .align(Alignment.CenterStart)
                )
                Image(
                    painter = painterResource(R.drawable.ic_more_vertical),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Gray000),
                    modifier = Modifier
                        .clickable {
                            onClickRelationInfo()
                        }
                        .align(Alignment.CenterEnd)
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Box(
                modifier = Modifier
                    .background(
                        color = Color(0x33FFFFFF),
                        shape = RoundedCornerShape(100.dp)
                    )
                    .padding(
                        horizontal = 10.dp,
                        vertical = 4.dp
                    )
            ) {
                Text(
                    text = "LV${currentGrowthType.level}. ${currentGrowthType.typeName}",
                    style = Body2.merge(
                        color = Gray000,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = model.relationDetail.name,
                    style = Headline0.merge(
                        color = Gray000,
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Text(
                    text = "・",
                    style = Headline0.merge(
                        color = Gray000,
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Text(
                    text = model.relationDetail.group.name,
                    style = Headline0.merge(
                        color = Gray000,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
            Spacer(modifier = Modifier.height(18.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "받은 마음",
                    style = Body1.merge(
                        color = Gray100,
                        fontWeight = FontWeight.Medium
                    )
                )
                Spacer(modifier = Modifier.width(14.dp))
                Text(
                    text = "${model.relationDetail.takeMoney}원",
                    style = Headline3.merge(
                        color = Gray000,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "보낸 마음",
                    style = Body1.merge(
                        color = Gray100,
                        fontWeight = FontWeight.Medium
                    )
                )
                Spacer(modifier = Modifier.width(14.dp))
                Text(
                    text = "-${model.relationDetail.giveMoney}원",
                    style = Headline3.merge(
                        color = Gray000,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }
    }
}

@Preview
@Composable
fun HistoryDetailBackgroundComponentPreview() {
    HistoryDetailBackgroundComponent(
        currentGrowthType = HistoryDetailGrowthType.LEVEL_SIX,
        model = HistoryDetailModel(
            state = HistoryDetailState.Init,
            relationDetail = RelationDetailWithUserInfo(
                id = 0L,
                name = "김진우",
                imageUrl = "",
                memo = "무르는 경사비 관리앱으로 사용자가 다양한 개인적인 축하 상황에 대해 금전적 기여를 쉽게 할 수 있게 돕는 모바일 애플리케이션입니다",
                group = RelationDetailGroup(
                    id = 0,
                    name = "친척"
                ),
                giveMoney = 100000L,
                takeMoney = 100000L
            ),
            hearts = listOf(
                RelatedHeart(
                    id = 0,
                    give = true,
                    money = 10000,
                    day = LocalDate(2024, 1, 23),
                    event = "생일",
                    memo = "메모 내용 한 줄 넘어가면 숨김 처리",
                    tags = listOf()
                ),
                RelatedHeart(
                    id = 0,
                    give = true,
                    money = 10000,
                    day = LocalDate(2024, 1, 24),
                    event = "생일",
                    memo = "메모 내용 한 줄 넘어가면 숨김 처리",
                    tags = HistoryTagType.entries.map { it.tagName }
                ),
                RelatedHeart(
                    id = 0,
                    give = false,
                    money = 500000,
                    day = LocalDate(2024, 1, 25),
                    event = "생일",
                    memo = "메모 내용 한 줄 넘어가면 숨김 처리 메모 내용 한 줄 넘어가면 숨김 처리 ",
                    tags = listOf("참석", "현금")
                ),
                RelatedHeart(
                    id = 0,
                    give = false,
                    money = 10000,
                    day = LocalDate(2024, 1, 26),
                    event = "생일",
                    memo = "메모 내용 한 줄 넘어가면 숨김 처리",
                    tags = listOf()
                ),
                RelatedHeart(
                    id = 0,
                    give = true,
                    money = 10000,
                    day = LocalDate(2024, 1, 27),
                    event = "생일",
                    memo = "메모 내용 한 줄 넘어가면 숨김 처리",
                    tags = listOf()
                ),
            )
        ),
        contentHeight = 480.dp,
        onClickRelationInfo = {},
        onClickGrowthInfo = {},
        onClickBack = {}
    )
}
