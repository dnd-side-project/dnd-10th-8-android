package ac.dnd.mour.android.presentation.ui.main.registration.main.component

import ac.dnd.mour.android.presentation.common.theme.Body1
import ac.dnd.mour.android.presentation.common.theme.Gray700
import ac.dnd.mour.android.presentation.common.theme.Space12
import ac.dnd.mour.android.presentation.common.view.textfield.UserDateTypingField
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.text.font.FontWeight

@Composable
fun RegistraionUserDate(
    focusManager: FocusManager,
    userYearText: String,
    userMonthText: String,
    userDayText: String,
    onUserYearTextChange: (String) -> Unit,
    onUserMonthTextChange: (String) -> Unit,
    onUserDayTextChange: (String) -> Unit,
) {
    Text(
        text = "생년월일",
        style = Body1.merge(
            color = Gray700,
            fontWeight = FontWeight.SemiBold
        )
    )
    Spacer(Modifier.height(Space12))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(modifier = Modifier.weight(114f)) {
            UserDateTypingField(
                text = userYearText,
                hintText = "YYYY",
                onValueChange = {
                    if (userYearText.length < 4) {
                        onUserYearTextChange(it)
                        if (it.length == 4) focusManager.moveFocus(FocusDirection.Right)
                    }
                },
            )
        }
        Spacer(Modifier.weight(10f))
        Box(modifier = Modifier.weight(90f)) {
            UserDateTypingField(
                text = userMonthText,
                hintText = "MM",
                onValueChange = {
                    if (userMonthText.length < 2) {
                        onUserMonthTextChange(it)
                        if (it.length == 2) focusManager.moveFocus(FocusDirection.Right)
                    }
                },
            )
        }
        Spacer(Modifier.weight(10f))
        Box(modifier = Modifier.weight(90f)) {
            UserDateTypingField(
                text = userDayText,
                hintText = "DD",
                onValueChange = {
                    if (userDayText.length < 2) onUserDayTextChange(it)
                },
            )
        }
    }
}
