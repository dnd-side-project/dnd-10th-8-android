package ac.dnd.mour.android.presentation.common.view.calendar

import ac.dnd.mour.android.presentation.R
import ac.dnd.mour.android.presentation.common.theme.Body1
import ac.dnd.mour.android.presentation.common.theme.Body2
import ac.dnd.mour.android.presentation.common.theme.Gray000
import ac.dnd.mour.android.presentation.common.theme.Gray300
import ac.dnd.mour.android.presentation.common.theme.Gray600
import ac.dnd.mour.android.presentation.common.theme.Gray700
import ac.dnd.mour.android.presentation.common.theme.Gray800
import ac.dnd.mour.android.presentation.common.theme.Primary4
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CalendarComponent(
    modifier: Modifier = Modifier,
    selectedYear: Int = 0,
    selectedMonth: Int = 0,
    selectedDay: Int = -1,
    verticalSpace: Dp = 0.dp,
    backgroundImageSize: Dp = 30.dp,
    calendarConfig: CalendarConfig,
    calendarColorProperties: CalendarColorProperties = CalendarColorProperties(
        otherMonthColor = Color.Transparent,
        todayBeforeColor = Gray800,
        todayColor = Gray700,
        todayAfterColor = Gray800
    ),
    unClickableDays: Set<CalendarDayType> = setOf(CalendarDayType.AFTER_TODAY),
    onDaySelect: (Int) -> Unit,
    itemContent: @Composable (dayItem: CalendarDay) -> Unit = { }
) {
    val dayItems = remember { mutableStateListOf<CalendarDay>() }
    LaunchedEffect(selectedYear, selectedMonth) {
        dayItems.clear()
        dayItems.addAll(calendarConfig.getCurrentCalendarDate(selectedYear, selectedMonth))
        onDaySelect(selectedDay)
    }

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalArrangement = Arrangement.spacedBy(verticalSpace)
        ) {
            items(calendarConfig.getDayOfWeek()) {
                Box(
                    modifier = Modifier.aspectRatio(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = it,
                        style = Body2.merge(
                            color = Gray700,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }
            items(dayItems) { dayItem ->
                val dayItemColor = animateColorAsState(
                    targetValue = if (selectedDay == dayItem.day && dayItem.dayType != CalendarDayType.OTHER_MONTH) Gray000
                    else when (dayItem.dayType) {
                        CalendarDayType.BEFORE_TODAY -> calendarColorProperties.todayBeforeColor
                        CalendarDayType.TODAY -> calendarColorProperties.todayAfterColor
                        CalendarDayType.AFTER_TODAY -> calendarColorProperties.todayAfterColor
                        CalendarDayType.OTHER_MONTH -> Color.Transparent
                    },
                    label = "text color"
                )
                val dayItemBackGroundColor = animateColorAsState(
                    targetValue = if (dayItem.day == selectedDay && dayItem.dayType != CalendarDayType.OTHER_MONTH) Primary4
                    else if (dayItem.dayType == CalendarDayType.TODAY) Gray300
                    else Color.Transparent,
                    label = "text bacgound color"
                )

                Column(
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable {
                            if (!unClickableDays.contains(dayItem.dayType) && dayItem.dayType != CalendarDayType.OTHER_MONTH)
                                onDaySelect(dayItem.day)
                        },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier.aspectRatio(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(backgroundImageSize)
                                .background(
                                    color = dayItemBackGroundColor.value,
                                    shape = CircleShape
                                )
                        )
                        Text(
                            text = dayItem.day.toString(),
                            style = Body1.merge(
                                color = dayItemColor.value,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                    if (dayItem.dayType != CalendarDayType.OTHER_MONTH) {
                        itemContent(dayItem)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun CalendarWithIconPreview() {
    CalendarComponent(
        modifier = Modifier
            .background(Color.White)
            .padding(horizontal = 20.dp),
        calendarConfig = CalendarConfig(),
        selectedYear = 2023,
        selectedMonth = 12,
        onDaySelect = {}
    ) { _ ->
        Image(
            painter = painterResource(R.drawable.ic_check_circle),
            contentDescription = null,
            modifier = Modifier.size(5.dp)
        )
    }
}

@Preview
@Composable
fun CalendarSelectedPreview() {
    val selectedDay by remember { mutableIntStateOf(10) }
    CalendarComponent(
        modifier = Modifier
            .background(Color.White)
            .padding(horizontal = 20.dp),
        selectedYear = 2024,
        selectedMonth = 2,
        calendarConfig = CalendarConfig(),
        selectedDay = selectedDay,
        onDaySelect = {}
    )
}

@Preview
@Composable
fun CalendarBottomSheetPreview() {
    val calendarConfig = CalendarConfig()
    var selectedYear by remember { mutableIntStateOf(calendarConfig.getCalendarYear()) }
    var selectedMonth by remember { mutableIntStateOf(calendarConfig.getCalendarMonth()) }
    var selectedDay by remember { mutableIntStateOf(calendarConfig.getCalendarDay()) }
    Column(
        modifier = Modifier.background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(R.drawable.ic_chevron_left),
                contentDescription = null,
                modifier = Modifier.clickable {
                    if (selectedMonth > 1) {
                        selectedMonth -= 1
                    } else {
                        selectedMonth = 12
                        selectedYear -= 1
                    }
                }
            )
            Text(
                text = "$selectedYear-$selectedMonth",
                fontSize = 20.sp,
                color = Color.Black,
                modifier = Modifier
                    .padding(vertical = 10.dp)
            )
            Image(
                painter = painterResource(R.drawable.ic_chevron_right),
                contentDescription = null,
                modifier = Modifier.clickable {
                    if (selectedMonth < 12) {
                        selectedMonth += 1
                    } else {
                        selectedMonth = 1
                        selectedYear += 1
                    }
                }
            )
        }
        CalendarComponent(
            modifier = Modifier.padding(36.dp),
            calendarConfig = calendarConfig,
            selectedYear = selectedYear,
            selectedMonth = selectedMonth,
            selectedDay = selectedDay,
            onDaySelect = {
                selectedDay = it
            },
            unClickableDays = setOf()
        )
    }
}
