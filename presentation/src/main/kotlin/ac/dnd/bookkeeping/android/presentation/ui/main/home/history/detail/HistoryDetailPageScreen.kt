package ac.dnd.bookkeeping.android.presentation.ui.main.home.history.detail

import ac.dnd.bookkeeping.android.presentation.R
import ac.dnd.bookkeeping.android.presentation.common.theme.Body1
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray000
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray200
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray400
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray500
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray600
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray700
import ac.dnd.bookkeeping.android.presentation.common.theme.Primary4
import ac.dnd.bookkeeping.android.presentation.common.theme.Shapes
import ac.dnd.bookkeeping.android.presentation.common.theme.Space24
import ac.dnd.bookkeeping.android.presentation.common.theme.Space4
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.bookkeeping.android.presentation.common.view.SnackBarScreen
import ac.dnd.bookkeeping.android.presentation.model.history.HistoryDetailSortedType
import ac.dnd.bookkeeping.android.presentation.model.history.HistorySortedType
import ac.dnd.bookkeeping.android.presentation.model.history.HistoryViewType
import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import ac.dnd.bookkeeping.android.presentation.ui.main.home.history.detail.edit.HistoryDetailEditScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HistoryDetailPageScreen(
    appState: ApplicationState,
    model: HistoryDetailModel,
    event: EventFlow<HistoryDetailEvent>,
    intent: (HistoryDetailIntent) -> Unit,
    handler: CoroutineExceptionHandler,
    viewType: HistoryViewType
) {
    val scope = rememberCoroutineScope()
    var isDropDownMenuExpanded by remember { mutableStateOf(false) }
    var viewSortType by remember { mutableStateOf(HistoryDetailSortedType.LATEST) }
    var isShowingSuccessDeleteSnackBar by remember { mutableStateOf(false) }
    var currentSelectedHeartIndex by remember { mutableIntStateOf(-1) }
    val hearts = model.hearts
        .filter { heart ->
            return@filter when (viewType) {
                HistoryViewType.TOTAL -> true
                HistoryViewType.GIVE -> heart.give
                HistoryViewType.TAKE -> !heart.give
            }
        }
        .sortedByDescending {
            if (viewSortType == HistoryDetailSortedType.LATEST) it.day else null
        }
        .sortedBy {
            if (viewSortType == HistoryDetailSortedType.OLDEST) it.day else null
        }


    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .padding(start = 2.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "내역 ${hearts.size}",
                    style = Body1.merge(
                        color = Gray600,
                        fontWeight = FontWeight.SemiBold
                    ),
                    modifier = Modifier.align(Alignment.CenterStart)
                )
                Row(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .clickable {
                            isDropDownMenuExpanded = true
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = viewSortType.typeName,
                        style = Body1.merge(
                            color = Gray700,
                            fontWeight = FontWeight.Medium
                        )
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Image(
                        painter = painterResource(R.drawable.ic_chevron_down),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    DropdownMenu(
                        modifier = Modifier
                            .wrapContentHeight()
                            .background(
                                color = Gray000,
                                shape = Shapes.medium
                            ),
                        expanded = isDropDownMenuExpanded,
                        onDismissRequest = { isDropDownMenuExpanded = false }
                    ) {
                        Column(verticalArrangement = Arrangement.Center) {
                            HistoryDetailSortedType.entries.forEachIndexed { index, type ->
                                DropdownMenuItem(
                                    onClick = {},
                                    contentPadding = PaddingValues(0.dp),
                                    modifier = Modifier
                                        .width(116.dp)
                                        .height(40.dp)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .clickable {
                                                viewSortType = type
                                                isDropDownMenuExpanded = false
                                            }
                                            .padding(horizontal = 8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        if (viewSortType == type) {
                                            Image(
                                                painter = painterResource(R.drawable.ic_check_line),
                                                contentDescription = null,
                                                colorFilter = ColorFilter.tint(Primary4),
                                                modifier = Modifier.size(Space24)
                                            )
                                        } else {
                                            Box(
                                                modifier = Modifier
                                                    .size(Space24)
                                                    .background(Color.White)
                                            )
                                        }
                                        Spacer(Modifier.width(Space4))
                                        Text(
                                            text = type.typeName,
                                            style = Body1.merge(
                                                color = Gray700,
                                                fontWeight = FontWeight.Normal
                                            )
                                        )
                                    }
                                }
                                if (index != HistorySortedType.entries.lastIndex) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(1.dp)
                                            .padding(top = 0.5.dp)
                                            .background(color = Gray200)
                                    )
                                }
                            }
                        }
                    }
                }
            }
            if (hearts.isEmpty()) {
                EmptyHeartView()
            } else {
                Spacer(modifier = Modifier.height(16.dp))
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(hearts.size) { index ->
                        HistoryDetailItem(
                            relatedHeart = hearts[index],
                            onClick = {
                                currentSelectedHeartIndex = index
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        if (currentSelectedHeartIndex != -1) {
            HistoryDetailEditScreen(
                relatedHeart = hearts[currentSelectedHeartIndex],
                appState = appState,
                onDismissRequest = {
                    currentSelectedHeartIndex = -1
                },
                onDelete = { id ->
                    intent(HistoryDetailIntent.OnDelete(id))
                    currentSelectedHeartIndex = -1
                    scope.launch {
                        isShowingSuccessDeleteSnackBar = true
                        delay(200L)
                        isShowingSuccessDeleteSnackBar = false
                    }
                },
                onEdit = { heart ->
                    intent(HistoryDetailIntent.OnEdit(heart))
                }
            )
        }

        if (isShowingSuccessDeleteSnackBar) {
            Box(modifier = Modifier.align(Alignment.TopCenter)) {
                SnackBarScreen("삭제가 완료되었습니다.")
            }
        }
    }
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
