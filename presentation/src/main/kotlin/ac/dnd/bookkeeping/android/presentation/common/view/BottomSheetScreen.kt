package ac.dnd.bookkeeping.android.presentation.common.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
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
import androidx.compose.ui.unit.dp
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialog
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialogProperties

@Composable
fun BottomSheetScreen(
    bottomSheetState: MutableState<Boolean>,
    content: @Composable () -> Unit = { SampleBottomSheetComponent(bottomSheetState) }
) {
    val animateIn = remember { mutableStateOf(false) }

    if (bottomSheetState.value) {
        BottomSheetDialog(
            onDismissRequest = {
                bottomSheetState.value = false
            },
            properties = BottomSheetDialogProperties(
                dismissOnClickOutside = true,
                dismissOnBackPress = true
            )
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                LaunchedEffect(Unit) { animateIn.value = true }
                AnimatedVisibility(
                    visible = animateIn.value && bottomSheetState.value,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Box(
                        modifier = Modifier
                            .shadow(
                                elevation = 8.dp,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(color = Color.LightGray),
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
fun SampleBottomSheetComponent(
    bottomSheetState: MutableState<Boolean>
) {

    Column(
        Modifier
            .padding(horizontal = 8.dp)
            .background(Color.Transparent)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(
                    Color.LightGray,
                    RoundedCornerShape(13.dp)
                )
        ) {

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "권한 추가",
                color = Color.Black,
            )
            Text(
                text = "사용 권한이 필요합니다.",
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(12.dp))

            Divider(
                modifier = Modifier.height(1.dp),
                color = Color.Black
            )

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        bottomSheetState.value = false
                    }
            ) {
                Text(
                    text = "권한 허용",
                    color = Color.Black,
                    modifier = Modifier.padding(vertical = 15.dp)
                )
            }
            Divider(
                modifier = Modifier.height(1.dp),
                color = Color.Black
            )

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        bottomSheetState.value = false
                    }
            ) {
                Text(
                    text = "취소",
                    color = Color.Black,
                    modifier = Modifier.padding(vertical = 15.dp)
                )
            }
        }
    }
}
