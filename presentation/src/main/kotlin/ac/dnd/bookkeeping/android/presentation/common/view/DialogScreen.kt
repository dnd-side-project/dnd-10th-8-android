package ac.dnd.bookkeeping.android.presentation.common.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

// 수정했음!!!
@Composable
fun DialogScreen(
    dialogIsShowingState: MutableState<Boolean>,
    width: Dp = 300.dp,
    content: @Composable () -> Unit = { SampleDialogComponent(dialogIsShowingState) }
) {
    val animateIn = remember { mutableStateOf(false) }

    if (dialogIsShowingState.value) {
        Dialog(
            onDismissRequest = {
                dialogIsShowingState.value = false
            }
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                LaunchedEffect(Unit) { animateIn.value = true }
                AnimatedVisibility(
                    visible = animateIn.value && dialogIsShowingState.value,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Box(
                        modifier = Modifier
                            .shadow(
                                elevation = 8.dp,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .width(width)
                            .clip(RoundedCornerShape(16.dp))
                            .background(
                                color = Color.LightGray
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        content()
                    }
                }
            }
        }
    }
}

@Composable
fun SampleDialogComponent(
    dialogState: MutableState<Boolean>
) {
    Surface(
        modifier = Modifier.background(
            color = Color.White,
            shape = RoundedCornerShape(8.dp)
        ),
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Sample Dialog Component",
                fontSize = 16.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(50.dp))
            Row(modifier = Modifier.wrapContentSize()) {
                Box(
                    modifier = Modifier
                        .background(
                            color = Color.Gray,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .weight(1f)
                        .clickable {
                            dialogState.value = false
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "취소",
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(
                                vertical = 10.5.dp,
                                horizontal = 12.dp
                            ),
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Box(
                    modifier = Modifier
                        .background(
                            color = Color.Gray,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .weight(1f)
                        .clickable {
                            dialogState.value = false
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "확인",
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(
                                vertical = 10.5.dp,
                                horizontal = 12.dp
                            )
                    )
                }
            }
        }
    }
}
