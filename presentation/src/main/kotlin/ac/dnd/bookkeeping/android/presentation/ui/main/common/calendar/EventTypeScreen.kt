package ac.dnd.bookkeeping.android.presentation.ui.main.common.calendar

import ac.dnd.bookkeeping.android.presentation.common.theme.Gray000
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray150
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray200
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray600
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray800
import ac.dnd.bookkeeping.android.presentation.common.theme.Headline3
import ac.dnd.bookkeeping.android.presentation.common.theme.Primary1
import ac.dnd.bookkeeping.android.presentation.common.theme.Space12
import ac.dnd.bookkeeping.android.presentation.common.theme.Space16
import ac.dnd.bookkeeping.android.presentation.common.theme.Space20
import ac.dnd.bookkeeping.android.presentation.model.history.HistoryEventType
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.holix.android.bottomsheetdialog.compose.BottomSheetBehaviorProperties
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialog
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialogProperties
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun EventTypeScreen(
    onDismissRequest: () -> Unit,
    onConfirm: (String) -> Unit
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
                    .fillMaxWidth()
                    .padding(
                        horizontal = Space20,
                        vertical = Space12
                    )
            ) {
                Text(
                    text = "경사 종류",
                    style = Headline3.merge(
                        color = Gray800,
                        fontWeight = FontWeight.SemiBold
                    ),
                    modifier = Modifier.align(Alignment.CenterStart)
                )
                Text(
                    text = "완료",
                    style = Headline3.merge(
                        color = Gray600,
                        fontWeight = FontWeight.Normal
                    ),
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .clickable {
                            if (selectedEventId!= -1L){
                                onConfirm(
                                    HistoryEventType.getEventName(selectedEventId)
                                )
                            }
                        }
                )
            }
            LazyColumn {
                items(HistoryEventType.entries) { type ->
                    var clickState by remember { mutableStateOf(false) }
                    val backgroundColor = animateColorAsState(
                        targetValue =
                        if (clickState) Gray150 else if (selectedEventId == type.id) Primary1 else Gray000,
                        label = ""
                    )
                    Box(
                        modifier = Modifier
                            .background(backgroundColor.value)
                            .fillMaxWidth()
                            .clickable {
                                selectedEventId =
                                    if (selectedEventId == type.id) -1 else type.id

                                scope.launch {
                                    clickState = true
                                    delay(100L)
                                    clickState = false
                                }

                            }
                            .padding(
                                horizontal = Space20,
                                vertical = Space16
                            )
                    ){
                        Text(
                            text = type.eventName,
                            style = Headline3.merge(
                                color = Gray800,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                }

                //TODO 직접 입력 케이스 추가
            }
        }
    }
}

@Preview
@Composable
fun EventTypeScreenPreview(){
    EventTypeScreen(
        onDismissRequest = {

        },
        onConfirm = {

        }
    )
}
