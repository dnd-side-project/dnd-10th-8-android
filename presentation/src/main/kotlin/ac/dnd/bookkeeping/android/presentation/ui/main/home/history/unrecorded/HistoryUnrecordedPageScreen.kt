package ac.dnd.bookkeeping.android.presentation.ui.main.home.history.unrecorded

import ac.dnd.bookkeeping.android.domain.model.feature.schedule.UnrecordedSchedule
import ac.dnd.bookkeeping.android.presentation.R
import ac.dnd.bookkeeping.android.presentation.common.theme.Body0
import ac.dnd.bookkeeping.android.presentation.common.theme.Body1
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray000
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray200
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray500
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray600
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray700
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray800
import ac.dnd.bookkeeping.android.presentation.common.theme.Headline1
import ac.dnd.bookkeeping.android.presentation.common.theme.Headline2
import ac.dnd.bookkeeping.android.presentation.common.theme.Headline3
import ac.dnd.bookkeeping.android.presentation.common.view.chip.ChipItem
import ac.dnd.bookkeeping.android.presentation.common.view.chip.ChipType
import ac.dnd.bookkeeping.android.presentation.common.view.component.FieldSubject
import ac.dnd.bookkeeping.android.presentation.common.view.confirm.ConfirmButton
import ac.dnd.bookkeeping.android.presentation.common.view.confirm.ConfirmButtonProperties
import ac.dnd.bookkeeping.android.presentation.common.view.confirm.ConfirmButtonSize
import ac.dnd.bookkeeping.android.presentation.common.view.confirm.ConfirmButtonType
import ac.dnd.bookkeeping.android.presentation.common.view.textfield.TypingPriceField
import ac.dnd.bookkeeping.android.presentation.model.history.HistoryEventType
import ac.dnd.bookkeeping.android.presentation.model.history.HistoryTagType
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun HistoryUnrecordedPageScreen(
    unRecordSize: Int,
    unrecordedSchedule: UnrecordedSchedule,
    intent: (HistoryUnrecordedIntent) -> Unit
) {
    var moneyText by remember { mutableStateOf("") }
    val tagIdList = remember { mutableStateListOf<Long>() }
    val scrollState = rememberScrollState()
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .background(color = Gray200)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .align(Alignment.TopStart)
                ) {
                    Text(
                        text = "지난 일정이 \n${unRecordSize}개 있습니다.",
                        style = Headline1.merge(
                            color = Gray700,
                            fontWeight = FontWeight.SemiBold
                        ),
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "얼마를 지출하셨나요?",
                        style = Body1.merge(
                            color = Gray600,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(end = 10.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_unrecorded_flower),
                        contentDescription = null,
                        modifier = Modifier.align(Alignment.TopEnd)
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .height(83.dp)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(
                            topStart = 8.dp,
                            bottomStart = 8.dp
                        )
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .width(6.dp)
                        .fillMaxHeight()
                        .background(
                            color = Color(HistoryEventType.getEventTypeColor(unrecordedSchedule.event)),
                            shape = RoundedCornerShape(
                                topStart = 8.dp,
                                bottomStart = 8.dp
                            )
                        )
                )
                Column(
                    modifier = Modifier
                        .padding(
                            start = 20.dp,
                            top = 12.dp,
                            bottom = 14.dp,
                            end = 20.dp
                        )
                ) {
                    Text(
                        text = "${unrecordedSchedule.day.year}년 " +
                                "${unrecordedSchedule.day.monthNumber}월 " +
                                "${unrecordedSchedule.day.dayOfMonth}일",
                        style = Body1.merge(
                            color = Gray600,
                            fontWeight = FontWeight.Normal
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = unrecordedSchedule.relation.name,
                            style = Headline2.merge(
                                color = Gray800,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                        Text(
                            text = "•",
                            style = Body0.merge(
                                color = Gray600,
                                fontWeight = FontWeight.Normal
                            ),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.width(16.dp)
                        )
                        Text(
                            text = unrecordedSchedule.relation.name,
                            style = Body0.merge(
                                color = Gray600,
                                fontWeight = FontWeight.Normal
                            )
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Box(
                            modifier = Modifier
                                .background(
                                    color = Color(
                                        HistoryEventType.getEventTypeColor(
                                            unrecordedSchedule.event
                                        )
                                    ),
                                    shape = RoundedCornerShape(100.dp)
                                )
                                .padding(
                                    horizontal = 12.dp,
                                    vertical = 3.5.dp
                                )
                        ) {
                            Text(
                                text = unrecordedSchedule.event,
                                style = Body1.merge(
                                    color = Gray000,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(28.dp))
            Column(
                modifier = Modifier
                    .background(Gray000)
                    .verticalScroll(scrollState)
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
            ) {
                Spacer(modifier = Modifier.height(24.dp))
                TypingPriceField(
                    modifier = Modifier.fillMaxWidth(),
                    hintText = "금액을 입력해주세요",
                    textValue = moneyText,
                    onValueChange = {
                        moneyText = it
                    }
                )
                Spacer(modifier = Modifier.height(40.dp))
                FieldSubject(
                    subject = "태그",
                    isViewIcon = false
                )
                Spacer(modifier = Modifier.height(6.dp))
                HistoryTagType.entries.chunked(5).forEach { registrationTagTypes ->
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        registrationTagTypes.forEach { type ->
                            ChipItem(
                                chipType = ChipType.BORDER,
                                chipText = type.tagName,
                                currentSelectedId = tagIdList.toSet(),
                                chipId = type.id,
                                onSelectChip = { selectId ->
                                    if (tagIdList.contains(selectId)) {
                                        tagIdList.remove(selectId)
                                    } else {
                                        tagIdList.add(selectId)
                                    }
                                },
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                }
                Spacer(modifier = Modifier.height(150.dp))
            }
        }
        Column(
            modifier = Modifier
                .background(color = Gray000)
                .align(Alignment.BottomCenter)
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(20.dp))
            ConfirmButton(
                modifier = Modifier.fillMaxWidth(),
                properties = ConfirmButtonProperties(
                    size = ConfirmButtonSize.Xlarge,
                    type = ConfirmButtonType.Primary
                ),
                isEnabled = moneyText.isNotEmpty(),
                onClick = {
                    intent(
                        HistoryUnrecordedIntent.OnRecord(
                            scheduleId = unrecordedSchedule.id,
                            money = moneyText.toLongOrNull() ?: 0L,
                            tags = HistoryTagType.getTagNameList(tagIdList)
                        )
                    )
                }
            ) {
                Text(
                    text = "다음",
                    style = Headline3.merge(
                        color = Gray000,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "건너뛰기",
                style = Headline3.merge(
                    color = Gray500,
                    fontWeight = FontWeight.SemiBold
                ),
                modifier = Modifier
                    .clickable {
                        intent(HistoryUnrecordedIntent.OnSkip(unrecordedSchedule.id))
                    },
            )
            Spacer(Modifier.height(20.dp))
        }
    }
}
