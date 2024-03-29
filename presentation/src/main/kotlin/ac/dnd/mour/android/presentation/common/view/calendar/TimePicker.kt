package ac.dnd.mour.android.presentation.common.view.calendar

import ac.dnd.mour.android.presentation.common.theme.Body0
import ac.dnd.mour.android.presentation.common.theme.Gray200
import ac.dnd.mour.android.presentation.common.theme.Gray500
import ac.dnd.mour.android.presentation.common.theme.Gray700
import ac.dnd.mour.android.presentation.common.theme.Gray800
import ac.dnd.mour.android.presentation.common.theme.Headline2
import ac.dnd.mour.android.presentation.common.theme.Headline3
import ac.dnd.mour.android.presentation.common.theme.Primary3
import ac.dnd.mour.android.presentation.common.theme.Primary4
import ac.dnd.mour.android.presentation.common.view.BottomSheetScreen
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
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
    onConfirm: (LocalTime?) -> Unit,

    ) {
    val timeTextStyle = Headline3.merge(
        color = Gray700,
        fontWeight = FontWeight.SemiBold
    )
    var timeType by remember {
        mutableStateOf(if (localTime.hour >= 12) TimePickerType.PM else TimePickerType.AM)
    }
    var hourValue by remember {
        mutableIntStateOf(
            if (localTime.hour > 12) localTime.hour - 12
            else if (localTime.hour == 0) 12
            else localTime.hour
        )
    }
    var minuteValue by remember { mutableIntStateOf(localTime.minute) }
    var isCheckNotValue by remember { mutableStateOf(false) }

    BottomSheetScreen(
        onDismissRequest = onDismissRequest,
        properties = properties
    ) {
        Column {
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
                            val transHour = when (timeType) {
                                TimePickerType.AM -> if (hourValue == 12) -12 else 0
                                TimePickerType.PM -> if (hourValue == 12) 0 else 12
                            }
                            onConfirm(
                                if (isCheckNotValue) {
                                    null
                                } else {
                                    LocalTime(
                                        hour = hourValue + transHour,
                                        minute = minuteValue
                                    )
                                }
                            )
                            onDismissRequest()
                        }
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                ListItemPicker(
                    value = timeType.typeName,
                    list = TimePickerType.getEntryNames(),
                    onValueChange = {
                        timeType = TimePickerType.getTimeType(it)
                    },
                    dividersColor = Primary3,
                    textStyle = timeTextStyle
                )
                Spacer(modifier = Modifier.width(40.dp))
                NumberPicker(
                    value = hourValue,
                    range = 1..12,
                    dividersColor = Primary3,
                    textStyle = timeTextStyle,
                    onValueChange = {
                        hourValue = it
                    },
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "시",
                    style = Body0.merge(
                        color = Gray800,
                        fontWeight = FontWeight.Normal
                    ),
                )
                Spacer(modifier = Modifier.width(26.dp))
                NumberPicker(
                    value = minuteValue,
                    range = 0..59,
                    dividersColor = Primary3,
                    textStyle = timeTextStyle,
                    onValueChange = {
                        minuteValue = it
                    },
                )
                Text(
                    text = "분",
                    fontWeight = FontWeight.Normal,
                    style = Body0.merge(color = Gray800),
                )
            }

            Spacer(modifier = Modifier.height(35.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(22.dp))
                RadioButton(
                    selected = isCheckNotValue,
                    onClick = {
                        isCheckNotValue = !isCheckNotValue
                    },
                    modifier = Modifier.size(16.dp),
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Primary4,
                        unselectedColor = Gray500
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "선택 안함",
                    fontWeight = FontWeight.Normal,
                    style = Body0.merge(color = Gray800),
                )
            }
            Spacer(modifier = Modifier.height(28.dp))
        }
    }
}

@Preview(apiLevel = 33)
@Composable
fun TimePickerPreview() {
    TimePicker(
        localTime = LocalTime(0, 0),
        onDismissRequest = {},
        onConfirm = {}
    )
}
