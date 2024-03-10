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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import kotlinx.datetime.LocalDate
import java.text.DecimalFormat

@Composable
fun HistoryDetailBackgroundComponent(
    currentGrowthType: HistoryDetailGrowthType,
    model: HistoryDetailModel,
    contentHeight: Dp,
    onClickRelationInfo: () -> Unit,
    onClickGrowthInfo: () -> Unit,
    onClickBack: () -> Unit
) {

    val animationBottomOffset = when (currentGrowthType) {
        HistoryDetailGrowthType.LEVEL_ONE -> (-42.73).dp
        HistoryDetailGrowthType.LEVEL_TWO -> (-3.38).dp
        HistoryDetailGrowthType.LEVEL_THREE -> 21.92.dp
        HistoryDetailGrowthType.LEVEL_FOUR -> 18.18.dp
        HistoryDetailGrowthType.LEVEL_FIVE -> 19.53.dp
        HistoryDetailGrowthType.LEVEL_SIX -> 9.37.dp
    }

    val animationEndOffset = when (currentGrowthType) {
        HistoryDetailGrowthType.LEVEL_ONE -> (-44.24).dp
        HistoryDetailGrowthType.LEVEL_TWO -> (-15.51).dp
        HistoryDetailGrowthType.LEVEL_THREE -> (-13.78).dp
        HistoryDetailGrowthType.LEVEL_FOUR -> (-12.74).dp
        HistoryDetailGrowthType.LEVEL_FIVE -> 3.72.dp
        HistoryDetailGrowthType.LEVEL_SIX -> 99.14.dp
    }

    val animationRaw = when (currentGrowthType) {
        HistoryDetailGrowthType.LEVEL_ONE -> R.raw.history_lv_1
        HistoryDetailGrowthType.LEVEL_TWO -> R.raw.history_lv_2
        HistoryDetailGrowthType.LEVEL_THREE -> R.raw.history_lv_3
        HistoryDetailGrowthType.LEVEL_FOUR -> R.raw.history_lv_4
        HistoryDetailGrowthType.LEVEL_FIVE -> R.raw.history_lv_5
        HistoryDetailGrowthType.LEVEL_SIX -> R.raw.history_lv_6
    }
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(resId = animationRaw)
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(contentHeight)
            .background(color = Color(currentGrowthType.backgroundColor))
    ) {

        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(
                    y = animationBottomOffset,
                    x = animationEndOffset
                )
        ) {
            LottieAnimation(
                composition = composition,
                iterations = LottieConstants.IterateForever
            )
        }

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
                    .clip(CircleShape)
                    .size(24.dp)
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
                val nameLength = model.relationDetail.name.length
                Text(
                    text = if (nameLength < 8) model.relationDetail.name else model.relationDetail.name.substring(
                        0,
                        8
                    ),
                    fontWeight = FontWeight.SemiBold,
                    style = Headline0.merge(color = Gray000),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
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
                    text = DecimalFormat("#,###").format(model.relationDetail.takeMoney) + "원",
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
                    text = "-" + DecimalFormat("#,###").format(model.relationDetail.giveMoney) + "원",
                    style = Headline3.merge(
                        color = Gray000,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }
    }
}

private val sampleHistoryDetailModel = HistoryDetailModel(
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
)

@Preview(apiLevel = 33)
@Composable
private fun HistoryDetailBackgroundComponent1Preview() {
    HistoryDetailBackgroundComponent(
        currentGrowthType = HistoryDetailGrowthType.LEVEL_SIX,
        model = sampleHistoryDetailModel,
        contentHeight = 480.dp,
        onClickRelationInfo = {},
        onClickGrowthInfo = {},
        onClickBack = {}
    )
}

@Preview(apiLevel = 33)
@Composable
private fun HistoryDetailBackgroundComponent2Preview() {
    HistoryDetailBackgroundComponent(
        currentGrowthType = HistoryDetailGrowthType.LEVEL_ONE,
        model = sampleHistoryDetailModel,
        contentHeight = 480.dp,
        onClickRelationInfo = {},
        onClickGrowthInfo = {},
        onClickBack = {}
    )
}

@Preview
@Composable
fun HistoryDetailBackgroundComponent3Preview() {
    HistoryDetailBackgroundComponent(
        currentGrowthType = HistoryDetailGrowthType.LEVEL_TWO,
        model = sampleHistoryDetailModel,
        contentHeight = 480.dp,
        onClickRelationInfo = {},
        onClickGrowthInfo = {},
        onClickBack = {}
    )
}

@Preview
@Composable
fun HistoryDetailBackgroundComponent4Preview() {
    HistoryDetailBackgroundComponent(
        currentGrowthType = HistoryDetailGrowthType.LEVEL_THREE,
        model = sampleHistoryDetailModel,
        contentHeight = 480.dp,
        onClickRelationInfo = {},
        onClickGrowthInfo = {},
        onClickBack = {}
    )
}

@Preview
@Composable
fun HistoryDetailBackgroundComponent5Preview() {
    HistoryDetailBackgroundComponent(
        currentGrowthType = HistoryDetailGrowthType.LEVEL_FOUR,
        model = sampleHistoryDetailModel,
        contentHeight = 480.dp,
        onClickRelationInfo = {},
        onClickGrowthInfo = {},
        onClickBack = {}
    )
}

@Preview
@Composable
fun HistoryDetailBackgroundComponent6Preview() {
    HistoryDetailBackgroundComponent(
        currentGrowthType = HistoryDetailGrowthType.LEVEL_FIVE,
        model = sampleHistoryDetailModel,
        contentHeight = 480.dp,
        onClickRelationInfo = {},
        onClickGrowthInfo = {},
        onClickBack = {}
    )
}
