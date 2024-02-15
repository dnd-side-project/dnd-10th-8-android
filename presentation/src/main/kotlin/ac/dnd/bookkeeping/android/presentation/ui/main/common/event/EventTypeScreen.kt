package ac.dnd.bookkeeping.android.presentation.ui.main.common.event

import ac.dnd.bookkeeping.android.presentation.common.theme.Gray000
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray200
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray300
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray600
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray800
import ac.dnd.bookkeeping.android.presentation.common.theme.Headline3
import ac.dnd.bookkeeping.android.presentation.common.theme.Primary1
import ac.dnd.bookkeeping.android.presentation.model.history.HistoryEventType
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.holix.android.bottomsheetdialog.compose.BottomSheetBehaviorProperties
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialog
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialogProperties

@Composable
fun EventTypeScreen(
    onDismissRequest: () -> Unit,
    onConfirm: (String) -> Unit,
    onTypingMode: () -> Unit
) {
    var selectedEventId by remember { mutableLongStateOf(-1) }
    val scope = rememberCoroutineScope()
    BottomSheetDialog(
        onDismissRequest = onDismissRequest,
        properties = BottomSheetDialogProperties(
            behaviorProperties = BottomSheetBehaviorProperties(
                state = BottomSheetBehaviorProperties.State.Expanded,
                skipCollapsed = true
            )
        )
    ) {
        Column(
            modifier = Modifier
                .background(Gray000)
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Box(
                modifier = Modifier
                    .background(Gray200)
                    .height(48.dp)
                    .fillMaxWidth()
            ) {
                Divider(
                    modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                        .align(Alignment.TopCenter),
                    color = Gray300
                )
                Text(
                    text = "경사 종류",
                    style = Headline3.merge(
                        color = Gray800,
                        fontWeight = FontWeight.SemiBold
                    ),
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 20.dp)
                )
                Text(
                    text = "완료",
                    style = Headline3.merge(
                        color = Gray600,
                        fontWeight = FontWeight.Normal
                    ),
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 20.dp)
                        .clickable {
                            if (selectedEventId == -2L) {
                                onTypingMode()
                            } else if (selectedEventId != -1L) {
                                onConfirm(
                                    HistoryEventType.getEventName(selectedEventId)
                                )
                            }
                        }
                )
                Divider(
                    modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter),
                    color = Gray300
                )
            }
            LazyColumn {
                items(HistoryEventType.entries) { type ->
                    val backgroundColor = animateColorAsState(
                        targetValue = if (selectedEventId == type.id) Primary1 else Gray000,
                        label = ""
                    )
                    Box(
                        modifier = Modifier
                            .background(backgroundColor.value)
                            .fillMaxWidth()
                            .height(56.dp)
                            .clickable {
                                selectedEventId = if (selectedEventId == type.id) -1 else type.id
                            }
                    ) {
                        Text(
                            text = type.eventName,
                            style = Headline3.merge(
                                color = Gray800,
                                fontWeight = FontWeight.SemiBold
                            ),
                            modifier = Modifier
                                .padding(start = 20.dp)
                                .align(Alignment.CenterStart)
                        )
                        Divider(
                            modifier = Modifier
                                .height(1.dp)
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)
                                .align(Alignment.BottomCenter),
                            color = Gray300
                        )
                    }
                }

                item(1) {
                    val backgroundColor = animateColorAsState(
                        targetValue = if (selectedEventId == -2L) Primary1 else Gray000,
                        label = ""
                    )
                    Box(
                        modifier = Modifier
                            .background(backgroundColor.value)
                            .fillMaxWidth()
                            .height(56.dp)
                            .clickable {
                                selectedEventId = -2L
                            }
                    ) {
                        Text(
                            text = "직접입력",
                            style = Headline3.merge(
                                color = Gray800,
                                fontWeight = FontWeight.SemiBold
                            ),
                            modifier = Modifier
                                .padding(start = 20.dp)
                                .align(Alignment.CenterStart)
                        )
                        Divider(
                            modifier = Modifier
                                .height(1.dp)
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)
                                .align(Alignment.BottomCenter),
                            color = Gray300
                        )
                    }
                }
            }
        }
    }
}

@Preview(apiLevel = 33)
@Composable
fun EventTypeScreenPreview() {
    EventTypeScreen(
        onDismissRequest = {

        },
        onConfirm = {

        },
        onTypingMode = {

        }
    )
}
