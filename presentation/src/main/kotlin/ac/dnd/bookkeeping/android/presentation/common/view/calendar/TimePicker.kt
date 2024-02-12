package ac.dnd.bookkeeping.android.presentation.common.view.calendar

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chargemap.compose.numberpicker.ListItemPicker
import com.chargemap.compose.numberpicker.NumberPicker
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialogProperties
import kotlinx.datetime.LocalTime

@Composable
fun TimePicker(
    localTime: LocalTime = LocalTime(0, 0),
    properties: BottomSheetDialogProperties = BottomSheetDialogProperties(),
    onDismissRequest: () -> Unit,
    onConfirm: (LocalTime) -> Unit
) {
    val timeTextStyle = Headline3.merge(
        color = Gray700,
        fontWeight = FontWeight.SemiBold
    )

    var hourValue by remember { mutableIntStateOf(localTime.hour) }
    var minuteValue by remember { mutableIntStateOf(localTime.minute) }
    var timeType by remember { mutableStateOf(TimePickerType.AM) }

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
                    text = "시간 선택",
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
                                LocalTime(
                                    hour = hourValue + when (timeType) {
                                        TimePickerType.AM -> 0
                                        TimePickerType.PM -> 12
                                    },
                                    minute = minuteValue
                                )
                            )
                            onDismissRequest()
                        }
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                NumberPicker(
                    value = hourValue,
                    range = 1..12,
                    dividersColor = Primary3,
                    textStyle = timeTextStyle,
                    onValueChange = {
                        hourValue = it
                    },
                )
                Spacer(modifier = Modifier.width(20.dp))
                NumberPicker(
                    value = minuteValue,
                    range = 0..59,
                    dividersColor = Primary3,
                    textStyle = timeTextStyle,
                    onValueChange = {
                        minuteValue = it
                    },
                )
                Spacer(modifier = Modifier.width(20.dp))
                ListItemPicker(
                    value = timeType.typeName,
                    list = TimePickerType.getEntryNames(),
                    onValueChange = {
                        timeType = TimePickerType.getTimeType(it)
                    },
                    dividersColor = Primary3,
                    textStyle = timeTextStyle
                )
            }
        }
    }
}

@Preview(apiLevel = 33)
@Composable
fun TimePickerPreview() {
    TimePicker(
        onDismissRequest = {},
        onConfirm = {}
    )
}
