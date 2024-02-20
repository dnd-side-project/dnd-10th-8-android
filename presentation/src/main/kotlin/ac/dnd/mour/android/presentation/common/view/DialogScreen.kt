package ac.dnd.mour.android.presentation.common.view

import ac.dnd.mour.android.presentation.R
import ac.dnd.mour.android.presentation.common.view.confirm.ConfirmButton
import ac.dnd.mour.android.presentation.common.view.confirm.ConfirmButtonProperties
import ac.dnd.mour.android.presentation.common.view.confirm.ConfirmButtonSize
import ac.dnd.mour.android.presentation.common.view.confirm.ConfirmButtonType
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
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
            shape = RoundedCornerShape(10.dp)
        ) {
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(top = 25.dp, start = 20.dp, end = 20.dp, bottom = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    fontSize = 24.sp,
                    color = Color.Black
                )

                if (message.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(15.dp))

                    Text(
                        text = message,
                        fontSize = 16.sp,
                        color = Color.Black,
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
                        ) { style ->
                            Text(
                                text = cancelMessage,
                                style = style
                            )
                        }

                        Spacer(modifier = Modifier.width(10.dp))
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
                    ) { style ->
                        Text(
                            text = confirmMessage,
                            style = style
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun DialogScreenPreview1() {
    DialogScreen(
        title = "제목",
        message = "내용\n여러줄 넘어가면 이렇게 됨.\n가가가가가가가가가가가가가가가가가가가가가가가",
        onCancel = {},
        onDismissRequest = {}
    )
}

@Preview
@Composable
fun DialogScreenPreview2() {
    DialogScreen(
        title = "제목",
        onDismissRequest = {}
    )
}