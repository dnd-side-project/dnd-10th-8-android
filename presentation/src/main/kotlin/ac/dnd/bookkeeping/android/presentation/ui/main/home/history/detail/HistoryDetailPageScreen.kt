package ac.dnd.bookkeeping.android.presentation.ui.main.home.history.detail

import ac.dnd.bookkeeping.android.presentation.common.theme.Body1
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray400
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray500
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray600
import ac.dnd.bookkeeping.android.presentation.common.theme.Shapes
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.bookkeeping.android.presentation.model.history.HistoryViewType
import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineExceptionHandler

@Composable
fun HistoryDetailPageScreen(
    appState: ApplicationState,
    model: HistoryDetailModel,
    event: EventFlow<HistoryDetailEvent>,
    intent: (HistoryDetailIntent) -> Unit,
    handler: CoroutineExceptionHandler,
    viewType: HistoryViewType
) {
}

@Composable
private fun EmptyHeartView() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.weight(44.39f))
        Text(
            text = "아직 주고 받은 내역이 없어요",
            style = Body1.merge(
                color = Gray600,
                fontWeight = FontWeight.SemiBold
            )
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "주고 받은 금액을 기록해 보세요",
            style = Body1.merge(
                color = Gray500,
                fontWeight = FontWeight.Normal
            )
        )
        Spacer(modifier = Modifier.height(24.dp))
        Box(
            modifier = Modifier
                .clip(Shapes.medium)
                .background(color = Color.White)
                .border(
                    width = 1.dp,
                    color = Gray400
                )
                .clickable {

                }
                .padding(
                    horizontal = 16.dp,
                    vertical = 6.5.dp
                )
        ) {
            Text(
                text = "마음 등록하기",
                style = Body1.merge(
                    fontWeight = FontWeight.SemiBold,
                    color = Gray500
                )
            )
        }
        Spacer(modifier = Modifier.weight(56.61f))
    }
}


@Preview
@Composable
private fun EmptyHeartPreview() {
    EmptyHeartView()
}
