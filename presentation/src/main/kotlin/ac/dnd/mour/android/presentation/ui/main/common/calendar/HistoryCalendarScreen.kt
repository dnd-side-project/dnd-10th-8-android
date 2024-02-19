package ac.dnd.mour.android.presentation.ui.main.common.calendar

import ac.dnd.mour.android.presentation.R
import ac.dnd.mour.android.presentation.common.theme.Gray000
import ac.dnd.mour.android.presentation.common.theme.Gray600
import ac.dnd.mour.android.presentation.common.theme.Gray700
import ac.dnd.mour.android.presentation.common.theme.Gray800
import ac.dnd.mour.android.presentation.common.theme.Headline2
import ac.dnd.mour.android.presentation.common.theme.Headline3
import ac.dnd.mour.android.presentation.common.theme.Space12
import ac.dnd.mour.android.presentation.common.theme.Space20
import ac.dnd.mour.android.presentation.common.theme.Space8
import ac.dnd.mour.android.presentation.common.view.BottomSheetScreen
import ac.dnd.mour.android.presentation.common.view.calendar.CalendarComponent
import ac.dnd.mour.android.presentation.common.view.calendar.CalendarConfig
import ac.dnd.mour.android.presentation.common.view.calendar.CalendarPicker
import ac.dnd.mour.android.presentation.common.view.confirm.ConfirmButton
import ac.dnd.mour.android.presentation.common.view.confirm.ConfirmButtonProperties
import ac.dnd.mour.android.presentation.common.view.confirm.ConfirmButtonSize
import ac.dnd.mour.android.presentation.common.view.confirm.ConfirmButtonType
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.holix.android.bottomsheetdialog.compose.BottomSheetBehaviorProperties
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialogProperties
import kotlinx.datetime.LocalDate

@Composable
fun HistoryCalendarScreen(
    calendarConfig: CalendarConfig,
    selectedYear: Int,
    selectedMonth: Int,
    selectedDay: Int,
    onClose: () -> Unit,
    onConfirm: (year: Int, month: Int, day: Int) -> Unit
) {
    var currentSelectYear by remember { mutableIntStateOf(selectedYear) }
    var currentSelectMonth by remember { mutableIntStateOf(selectedMonth) }
    var currentSelectDay by remember { mutableIntStateOf(selectedDay) }
    var isPickerShowing by remember { mutableStateOf(false) }
    BottomSheetScreen(
        onDismissRequest = onClose,
        properties = BottomSheetDialogProperties(
            behaviorProperties = BottomSheetBehaviorProperties(
                state = BottomSheetBehaviorProperties.State.Expanded,
                skipCollapsed = true
            )
        )
    ) {
        Column(
            modifier = Modifier
                .background(Color.Transparent)
                .wrapContentHeight()
                .padding(horizontal = Space20),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Space8)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_close),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable {
                            onClose()
                        }
                        .align(Alignment.CenterEnd)
                )
            }
            Text(
                text = "활동 날짜 선택",
                style = Headline2.merge(
                    color = Gray800,
                    fontWeight = FontWeight.SemiBold
                ),
                modifier = Modifier
                    .padding(vertical = Space8)
                    .fillMaxWidth(),
                textAlign = TextAlign.Left
            )
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 2.5.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_chevron_left),
                    colorFilter = ColorFilter.tint(Gray600),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .clickable {
                            if (currentSelectMonth > 1) {
                                currentSelectMonth -= 1
                            } else {
                                currentSelectMonth = 12
                                currentSelectYear -= 1
                            }
                        }
                )
                Row(
                    modifier = Modifier
                        .wrapContentWidth()
                        .clickable {
                            isPickerShowing = true
                        }
                        .align(Alignment.Center),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${currentSelectMonth}월",
                        style = Headline3.merge(
                            color = Gray700,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Image(
                        painter = painterResource(R.drawable.ic_chevron_up),
                        contentDescription = null,
                        modifier = Modifier
                            .size(16.dp)
                            .rotate(180f)
                    )
                }
                Image(
                    painter = painterResource(R.drawable.ic_chevron_right),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Gray600),
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .clickable {
                            if (currentSelectMonth < 12) {
                                currentSelectMonth += 1
                            } else {
                                currentSelectMonth = 1
                                currentSelectYear += 1
                            }
                        }
                )
            }

            CalendarComponent(
                modifier = Modifier.padding(vertical = 16.dp),
                calendarConfig = calendarConfig,
                selectedYear = currentSelectYear,
                selectedMonth = currentSelectMonth,
                selectedDay = currentSelectDay,
                onDaySelect = {
                    currentSelectDay = it
                },
                unClickableDays = setOf()
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = Space20,
                        vertical = Space12
                    )
            ) {
                ConfirmButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        onConfirm(
                            currentSelectYear,
                            currentSelectMonth,
                            currentSelectDay
                        )

                    },
                    properties = ConfirmButtonProperties(
                        size = ConfirmButtonSize.Xlarge,
                        type = ConfirmButtonType.Primary
                    )
                ) {
                    Text(
                        text = "선택 완료",
                        style = Headline3.merge(
                            color = Gray000,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }
        }

        if (isPickerShowing) {
            CalendarPicker(
                localDate = LocalDate(
                    year = currentSelectYear,
                    monthNumber = currentSelectMonth,
                    dayOfMonth = 1
                ),
                isDaySelectable = false,
                onDismissRequest = {
                    isPickerShowing = false
                },
                onConfirm = {
                    isPickerShowing = false
                    currentSelectYear = it.year
                    currentSelectMonth = it.monthNumber
                }
            )
        }
    }
}

@Preview(apiLevel = 33)
@Composable
fun HistoryCalendarScreenPreview() {
    HistoryCalendarScreen(
        calendarConfig = CalendarConfig(),
        selectedYear = 2024,
        selectedMonth = 1,
        selectedDay = 20,
        onClose = {},
        onConfirm = { _, _, _ -> }
    )
}
