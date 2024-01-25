package ac.dnd.bookkeeping.android.presentation.common.view

import ac.dnd.bookkeeping.android.presentation.R
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    isShowing: Boolean,
    isCancelable: Boolean = true,
    title: String = "",
    message: String = "",
    confirmMessage: String = stringResource(id = R.string.dialog_confirm),
    cancelMessage: String = stringResource(id = R.string.dialog_cancel),
    onConfirm: () -> Unit = {},
    onCancel: (() -> Unit)? = null,
    onDismissRequest: (() -> Unit),
) {

    if (isShowing) {
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
                                text = cancelMessage,
                                isMain = false,
                                modifier = Modifier.weight(1f),
                                onClick = {
                                    onCancel()
                                    onDismissRequest()
                                }
                            )

                            Spacer(modifier = Modifier.width(10.dp))
                        }

                        ConfirmButton(
                            text = confirmMessage,
                            isMain = true,
                            modifier = Modifier.weight(1f),
                            onClick = {
                                onConfirm()
                                onDismissRequest()
                            }
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
    var isShowing by remember { mutableStateOf(true) }

    DialogScreen(
        isShowing = isShowing,
        title = "제목",
        message = "내용\n여러줄 넘어가면 이렇게 됨.\n가가가가가가가가가가가가가가가가가가가가가가가",
        onCancel = {},
        onDismissRequest = {
            isShowing = false
        }
    )
}

@Preview
@Composable
fun DialogScreenPreview2() {
    var isShowing by remember { mutableStateOf(true) }

    DialogScreen(
        isShowing = isShowing,
        title = "제목",
        onDismissRequest = {
            isShowing = false
        }
    )
}
