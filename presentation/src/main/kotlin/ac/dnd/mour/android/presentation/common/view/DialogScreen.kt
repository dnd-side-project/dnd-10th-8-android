package ac.dnd.mour.android.presentation.common.view

import ac.dnd.mour.android.presentation.R
import ac.dnd.mour.android.presentation.common.theme.Body1
import ac.dnd.mour.android.presentation.common.theme.Gray000
import ac.dnd.mour.android.presentation.common.theme.Gray800
import ac.dnd.mour.android.presentation.common.theme.Gray900
import ac.dnd.mour.android.presentation.common.theme.Headline3
import ac.dnd.mour.android.presentation.common.theme.Shapes
import ac.dnd.mour.android.presentation.common.view.confirm.ConfirmButton
import ac.dnd.mour.android.presentation.common.view.confirm.ConfirmButtonProperties
import ac.dnd.mour.android.presentation.common.view.confirm.ConfirmButtonSize
import ac.dnd.mour.android.presentation.common.view.confirm.ConfirmButtonType
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun DialogScreen(
    isCancelable: Boolean = true,
    title: String = "",
    message: String = "",
    confirmMessage: String = stringResource(id = R.string.dialog_confirm),
    cancelMessage: String = stringResource(id = R.string.dialog_cancel),
    onConfirm: () -> Unit = {},
    onCancel: (() -> Unit)? = null,
    onDismissRequest: (() -> Unit),
) {
    Dialog(
        properties = DialogProperties(
            dismissOnBackPress = isCancelable,
            dismissOnClickOutside = isCancelable
        ),
        onDismissRequest = {
            onDismissRequest()
        }
    ) {
        Card(
            backgroundColor = Color.White,
            shape = Shapes.large
        ) {
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(15.dp))
                if (title.isNotEmpty()) {
                    Text(
                        text = title,
                        fontSize = 24.sp,
                        color = Color.Black
                    )
                }

                if (message.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(15.dp))

                    Text(
                        text = message,
                        style = Body1.merge(
                            color = Gray900,
                            fontWeight = FontWeight.SemiBold
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(modifier = Modifier.wrapContentSize()) {
                    if (onCancel != null) {
                        ConfirmButton(
                            properties = ConfirmButtonProperties(
                                size = ConfirmButtonSize.Large,
                                type = ConfirmButtonType.Secondary
                            ),
                            modifier = Modifier.weight(1f),
                            onClick = {
                                onCancel()
                                onDismissRequest()
                            }
                        ) {
                            Text(
                                text = cancelMessage,
                                style = Headline3.merge(
                                    color = Gray800,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                    }

                    ConfirmButton(
                        properties = ConfirmButtonProperties(
                            size = ConfirmButtonSize.Large,
                            type = ConfirmButtonType.Primary
                        ),
                        modifier = Modifier.weight(1f),
                        onClick = {
                            onConfirm()
                            onDismissRequest()
                        }
                    ) {
                        Text(
                            text = confirmMessage,
                            style = Headline3.merge(
                                color = Gray000,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Preview
@Composable
private fun DialogScreenPreview1() {
    DialogScreen(
        title = "제목",
        message = "내용\n여러줄 넘어가면 이렇게 됨.\n가가가가가가가가가가가가가가가가가가가가가가가",
        onCancel = {},
        onDismissRequest = {}
    )
}

@Preview
@Composable
private fun DialogScreenPreview2() {
    DialogScreen(
        title = "제목",
        onDismissRequest = {}
    )
}

@Preview
@Composable
private fun DialogScreenPreview3() {
    DialogScreen(
        message = "메시지",
        onCancel = {},
        onDismissRequest = {}
    )
}
