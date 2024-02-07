package ac.dnd.bookkeeping.android.presentation.common.view.calendar

import ac.dnd.bookkeeping.android.presentation.common.theme.Body0
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray200
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray700
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray800
import ac.dnd.bookkeeping.android.presentation.common.theme.Headline2
import ac.dnd.bookkeeping.android.presentation.common.theme.Headline3
import ac.dnd.bookkeeping.android.presentation.common.theme.Primary3
import ac.dnd.bookkeeping.android.presentation.common.theme.Primary4
import ac.dnd.bookkeeping.android.presentation.common.view.BottomSheetScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chargemap.compose.numberpicker.NumberPicker
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialogProperties
import kotlinx.datetime.LocalDate
import java.util.Calendar

@Composable
fun CalendarPicker(
    localDate: LocalDate,
    isDaySelectable: Boolean = true,
    properties: BottomSheetDialogProperties = BottomSheetDialogProperties(),
    onDismissRequest: () -> Unit,
    onConfirm: (LocalDate) -> Unit
) {
    val dateTextStyle = Headline3.merge(
        color = Gray700,
        fontWeight = FontWeight.SemiBold
    )
    val labelTextStyle = Body0.merge(
        color = Gray700,
        fontWeight = FontWeight.Normal
    )

    fun setDayValue(
        year: Int,
        month: Int
    ): Int {
        return Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month - 1)
        }.getActualMaximum(Calendar.DATE)
    }

    var yearValue by remember { mutableIntStateOf(localDate.year) }
    var monthValue by remember { mutableIntStateOf(localDate.monthNumber) }
    var dayValue by remember { mutableIntStateOf(localDate.dayOfMonth) }
    var dayMaxValue by remember {
        mutableIntStateOf(
            setDayValue(
                localDate.year,
                localDate.monthNumber
            )
        )
    }

    BottomSheetScreen(
        onDismissRequest = onDismissRequest,
        properties = properties
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .background(Gray200)
                    .fillMaxWidth()
                    .padding(
                        start = 22.dp,
                        end = 21.dp,
                        top = 16.dp,
                        bottom = 13.dp
                    )
            ) {
                Text(
                    text = "날짜 선택",
                    style = Headline2.merge(
                        color = Gray800,
                        fontWeight = FontWeight.SemiBold
                    ),
                    modifier = Modifier.align(Alignment.CenterStart)
                )
                Text(
                    text = "완료",
                    style = Headline3.merge(
                        color = Primary4,
                        fontWeight = FontWeight.SemiBold
                    ),
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .clickable {
                            onConfirm(
                                LocalDate(
                                    year = yearValue,
                                    monthNumber = monthValue,
                                    dayOfMonth = dayValue
                                )
                            )
                        }
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                NumberPicker(
                    value = yearValue,
                    range = localDate.year - 50..localDate.year + 50,
                    dividersColor = Primary3,
                    textStyle = dateTextStyle,
                    onValueChange = {
                        yearValue = it
                        dayMaxValue = setDayValue(it, monthValue)
                    },
                )
                Text(
                    text = "년",
                    style = labelTextStyle
                )
                Spacer(modifier = Modifier.width(10.dp))

                NumberPicker(
                    value = monthValue,
                    range = 1..12,
                    dividersColor = Primary3,
                    textStyle = dateTextStyle,
                    onValueChange = {
                        monthValue = it
                        dayMaxValue = setDayValue(yearValue, it)
                    },
                )
                Text(
                    text = "월",
                    style = labelTextStyle
                )

                if (isDaySelectable) {
                    Spacer(modifier = Modifier.width(10.dp))
                    NumberPicker(
                        value = dayValue,
                        range = 1..dayMaxValue,
                        dividersColor = Primary3,
                        textStyle = dateTextStyle,
                        onValueChange = {
                            dayValue = it
                        },
                    )
                    Text(
                        text = "일",
                        style = labelTextStyle
                    )
                }

            }
        }
    }
}

@Preview(apiLevel = 33)
@Composable
private fun Picker1Preview() {
    CalendarPicker(
        localDate = LocalDate(
            2024, 2, 7
        ),
        isDaySelectable = true,
        onDismissRequest = {

        },
        onConfirm = {

        }
    )
}

@Preview(apiLevel = 33)
@Composable
private fun Picker2Preview() {
    CalendarPicker(
        localDate = LocalDate(
            2024, 2, 7
        ),
        isDaySelectable = false,
        onDismissRequest = {

        },
        onConfirm = {

        }
    )
}
