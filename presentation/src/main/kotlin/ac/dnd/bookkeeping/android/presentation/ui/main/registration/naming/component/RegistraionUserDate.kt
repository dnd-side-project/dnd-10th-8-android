package ac.dnd.bookkeeping.android.presentation.ui.main.registration.naming.component

import ac.dnd.bookkeeping.android.presentation.R
import ac.dnd.bookkeeping.android.presentation.common.theme.Body1
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray400
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray600
import ac.dnd.bookkeeping.android.presentation.common.theme.Space12
import ac.dnd.bookkeeping.android.presentation.common.theme.Space48
import ac.dnd.bookkeeping.android.presentation.common.view.CustomTextField
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
fun RegistraionUserDate(
    focusManager: FocusManager,
    userYearText: TextFieldValue,
    userMonthText: TextFieldValue,
    userDayText: TextFieldValue,
    onUserYearTextChange: (TextFieldValue) -> Unit,
    onUserMonthTextChange: (TextFieldValue) -> Unit,
    onUserDayTextChange: (TextFieldValue) -> Unit,
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
        horizontalArrangement = Arrangement.spacedBy(Space12)
    ) {
        Surface(modifier = Modifier.weight(114f), color = Color.Transparent) {
            CustomTextField(
                text = userYearText.text,
                modifier = Modifier.background(Color.White),
                elevation = 0.dp,
                onTextChange = {
                    onUserYearTextChange(TextFieldValue(it))
                    if (userYearText.text.length == 4) focusManager.moveFocus(FocusDirection.Right)
                },
                height = Space48,
                cornerBorder = BorderStroke(
                    width = 1.dp,
                    color = Gray400
                ),
                contentInnerPadding = PaddingValues(horizontal = 16.dp),
                trailingIconContent = {
                    if (userYearText.text.isNotEmpty()) {
                        IconButton(
                            onClick = {
                                onUserYearTextChange(TextFieldValue())
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_close),
                                contentDescription = "close icon",
                            )
                        }
                    }
                },
                hintTextContent = { Text(text = "YYYY", style = Body1.merge(Gray600)) }
            )
        }
        Surface(modifier = Modifier.weight(90f), color = Color.Transparent) {
            CustomTextField(
                text = userMonthText.text,
                modifier = Modifier.background(Color.White),
                elevation = 0.dp,
                height = Space48,
                onTextChange = {
                    onUserMonthTextChange(TextFieldValue(it))
                    if (userMonthText.text.length == 2) focusManager.moveFocus(
                        FocusDirection.Right
                    )
                },
                cornerBorder = BorderStroke(
                    width = 1.dp,
                    color = Gray400
                ),
                contentInnerPadding = PaddingValues(horizontal = 16.dp),
                trailingIconContent = {
                    if (userMonthText.text.isNotEmpty()) {
                        IconButton(
                            onClick = {
                                onUserMonthTextChange(TextFieldValue())
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_close),
                                contentDescription = "close icon",
                            )
                        }
                    }
                },
                hintTextContent = { Text(text = "MM", style = Body1.merge(Gray600)) }
            )
        }
        Surface(modifier = Modifier.weight(90f), color = Color.Transparent) {
            CustomTextField(
                text = userDayText.text,
                height = Space48,
                elevation = 0.dp,
                modifier = Modifier.background(Color.White),
                onTextChange = {
                    onUserDayTextChange(TextFieldValue(it))
                },
                contentInnerPadding = PaddingValues(horizontal = 16.dp),
                cornerBorder = BorderStroke(
                    width = 1.dp,
                    color = Gray400
                ),
                trailingIconContent = {
                    if (userDayText.text.isNotEmpty()) {
                        IconButton(
                            onClick = {
                                onUserDayTextChange(TextFieldValue())
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_close),
                                contentDescription = "close icon",
                            )
                        }
                    }
                },
                hintTextContent = { Text(text = "DD", style = Body1.merge(Gray600)) }
            )
        }
    }
}
