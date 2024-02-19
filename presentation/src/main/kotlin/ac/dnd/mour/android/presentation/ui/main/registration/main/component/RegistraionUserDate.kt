package ac.dnd.mour.android.presentation.ui.main.registration.main.component

import ac.dnd.mour.android.presentation.R
import ac.dnd.mour.android.presentation.common.theme.Body1
import ac.dnd.mour.android.presentation.common.theme.Gray600
import ac.dnd.mour.android.presentation.common.theme.Space12
import ac.dnd.mour.android.presentation.common.theme.Space20
import ac.dnd.mour.android.presentation.common.view.textfield.TypingTextField
import ac.dnd.mour.android.presentation.common.view.textfield.TypingTextFieldType
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType

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
            color = Gray600,
            fontWeight = FontWeight.SemiBold
        )
    )
    Spacer(Modifier.height(Space12))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(modifier = Modifier.weight(114f)) {
            TypingTextField(
                textType = TypingTextFieldType.Basic,
                text = userYearText,
                hintText = "YYYY",
                onValueChange = {
                    if (userYearText.length < 4) {
                        onUserYearTextChange(it)
                        if (it.length == 4) focusManager.moveFocus(FocusDirection.Right)
                    }
                },
                trailingIconContent = {
                    if (userYearText.isNotEmpty()) {
                        IconButton(
                            onClick = {
                                onUserYearTextChange("")
                            }
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_close_circle),
                                contentDescription = "close icon",
                                modifier = Modifier.size(Space20)
                            )
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
        Spacer(Modifier.weight(10f))
        Box(modifier = Modifier.weight(90f)) {
            TypingTextField(
                textType = TypingTextFieldType.Basic,
                text = userMonthText,
                hintText = "MM",
                onValueChange = {
                    if (userMonthText.length < 2) {
                        onUserMonthTextChange(it)
                        if (it.length == 2) focusManager.moveFocus(FocusDirection.Right)
                    }
                },
                trailingIconContent = {
                    if (userMonthText.isNotEmpty()) {
                        IconButton(
                            onClick = {
                                onUserMonthTextChange("")
                            }
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_close_circle),
                                contentDescription = "close icon",
                                modifier = Modifier.size(Space20)
                            )
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
        Spacer(Modifier.weight(10f))
        Box(modifier = Modifier.weight(90f)) {
            TypingTextField(
                textType = TypingTextFieldType.Basic,
                text = userDayText,
                hintText = "DD",
                onValueChange = {
                    if (userDayText.length < 2) onUserDayTextChange(it)
                },
                trailingIconContent = {
                    if (userDayText.isNotEmpty()) {
                        IconButton(
                            onClick = {
                                onUserDayTextChange("")
                            }
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_close_circle),
                                contentDescription = "close icon",
                                modifier = Modifier.size(Space20)
                            )

                        }
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
    }
}
