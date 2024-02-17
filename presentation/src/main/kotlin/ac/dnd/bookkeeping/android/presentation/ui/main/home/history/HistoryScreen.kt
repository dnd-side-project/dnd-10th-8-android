package ac.dnd.bookkeeping.android.presentation.ui.main.home.history

import ac.dnd.bookkeeping.android.domain.model.feature.group.GroupWithRelationDetail
import ac.dnd.bookkeeping.android.domain.model.feature.relation.RelationDetail
import ac.dnd.bookkeeping.android.domain.model.feature.relation.RelationDetailGroup
import ac.dnd.bookkeeping.android.domain.model.feature.schedule.UnrecordedSchedule
import ac.dnd.bookkeeping.android.domain.model.feature.schedule.UnrecordedScheduleRelation
import ac.dnd.bookkeeping.android.domain.model.feature.schedule.UnrecordedScheduleRelationGroup
import ac.dnd.bookkeeping.android.presentation.R
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray000
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray200
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray300
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray500
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray600
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray800
import ac.dnd.bookkeeping.android.presentation.common.theme.Headline3
import ac.dnd.bookkeeping.android.presentation.common.theme.Primary3
import ac.dnd.bookkeeping.android.presentation.common.theme.Primary4
import ac.dnd.bookkeeping.android.presentation.common.theme.Secondary5
import ac.dnd.bookkeeping.android.presentation.common.theme.Secondary6
import ac.dnd.bookkeeping.android.presentation.common.util.ErrorObserver
import ac.dnd.bookkeeping.android.presentation.common.util.LaunchedEffectWithLifecycle
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.eventObserve
import ac.dnd.bookkeeping.android.presentation.common.util.expansion.addFocusCleaner
import ac.dnd.bookkeeping.android.presentation.common.view.textfield.TypingTextField
import ac.dnd.bookkeeping.android.presentation.common.view.textfield.TypingTextFieldType
import ac.dnd.bookkeeping.android.presentation.model.history.HistoryViewSwipingType
import ac.dnd.bookkeeping.android.presentation.model.history.HistoryViewType
import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import ac.dnd.bookkeeping.android.presentation.ui.main.home.common.relation.RelationConstant
import ac.dnd.bookkeeping.android.presentation.ui.main.home.history.registration.HistoryRegistrationConstant
import ac.dnd.bookkeeping.android.presentation.ui.main.rememberApplicationState
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

@Composable
fun HistoryScreen(
    appState: ApplicationState,
    viewModel: HistoryViewModel = hiltViewModel()
) {

    val model: HistoryModel = Unit.let {
        val state by viewModel.state.collectAsStateWithLifecycle()
        val unrecordedSchedule by viewModel.unrecordedSchedule.collectAsStateWithLifecycle()
        val groups by viewModel.groups.collectAsStateWithLifecycle()
        HistoryModel(
            state = state,
            unrecordedSchedule = unrecordedSchedule,
            groups = groups
        )
    }
    ErrorObserver(viewModel)

    HistoryScreen(
        appState = appState,
        model = model,
        event = viewModel.event,
        intent = viewModel::onIntent,
        handler = viewModel.handler
    )
}

@OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalMaterialApi::class,
    ExperimentalMotionApi::class
)
@Composable
private fun HistoryScreen(
    appState: ApplicationState,
    model: HistoryModel,
    event: EventFlow<HistoryEvent>,
    intent: (HistoryIntent) -> Unit,
    handler: CoroutineExceptionHandler
) {
    val focusManager = LocalFocusManager.current
    val contentHeight = (if (model.unrecordedSchedule.isEmpty()) 257.dp else 343.dp)
    val swipeState = rememberSwipeableState(initialValue = HistoryViewSwipingType.COLLAPSED)
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                return if (delta < 0) {
                    swipeState.performDrag(delta).toOffset()
                } else {
                    Offset.Zero
                }
            }

            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                val delta = available.y
                return swipeState.performDrag(delta).toOffset()
            }

            override suspend fun onPostFling(
                consumed: Velocity,
                available: Velocity
            ): Velocity {
                swipeState.performFling(velocity = available.y)
                return super.onPostFling(consumed, available)
            }

            private fun Float.toOffset() = Offset(0f, this)
        }
    }
    val computedProgress by remember {
        derivedStateOf {
            when (swipeState.progress.to) {
                HistoryViewSwipingType.EXPANDED -> swipeState.progress.fraction
                HistoryViewSwipingType.COLLAPSED -> 1f - swipeState.progress.fraction
            }
        }
    }
    val searchBoxHeightState = animateDpAsState(
        targetValue = when (swipeState.progress.to) {
            HistoryViewSwipingType.COLLAPSED -> 0.dp

            HistoryViewSwipingType.EXPANDED -> 65.dp
        },
        label = "search in EXPANDED "
    )
    val scope = rememberCoroutineScope()
    val pages = listOf("전체", "받은 마음", "보낸 마음")
    val pagerState = rememberPagerState(
        pageCount = { 3 }
    )
    var searchText by remember { mutableStateOf("") }
    var isTextFieldFocused by remember { mutableStateOf(false) }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Primary3)
            .addFocusCleaner(focusManager)
    ) {
        val heightInPx = with(LocalDensity.current) { maxHeight.toPx() }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .swipeable(
                    state = swipeState,
                    thresholds = { _, _ ->
                        FractionalThreshold(0.05f)
                    },
                    orientation = Orientation.Vertical,
                    anchors = mapOf(
                        0f to HistoryViewSwipingType.EXPANDED,
                        heightInPx to HistoryViewSwipingType.COLLAPSED
                    )
                )
                .nestedScroll(nestedScrollConnection)
        ) {
            HistoryBackgroundComponent(
                model = model,
                onClickAlarm = {

                },
                isTextFieldFocused = isTextFieldFocused,
                onFocusChange = {
                    isTextFieldFocused = it
                },
                searchText = searchText,
                onSearchValueChange = {
                    searchText = it
                },
                onClickUnrecorded = {
                    //TODO go Unrecorded
                },
                onDeleteUnrecorded = {
                    //TODO delete Unrecorded
                }
            )
            MotionLayout(
                modifier = Modifier.fillMaxSize(),
                start = ConstraintSet {
                    val header = createRefFor("header")
                    val body = createRefFor("body")
                    constrain(header) {
                        this.width = Dimension.matchParent
                        this.height = Dimension.value(contentHeight)
                    }
                    constrain(body) {
                        this.width = Dimension.matchParent
                        this.height = Dimension.fillToConstraints
                        this.top.linkTo(header.bottom, 0.dp)
                        this.bottom.linkTo(parent.bottom, 0.dp)
                    }
                },
                end = ConstraintSet {
                    val header = createRefFor("header")
                    val body = createRefFor("body")
                    constrain(header) {
                        this.width = Dimension.matchParent
                        this.height = Dimension.value(0.dp)
                    }
                    constrain(body) {
                        this.width = Dimension.matchParent
                        this.height = Dimension.fillToConstraints
                        this.top.linkTo(header.bottom, 0.dp)
                        this.bottom.linkTo(parent.bottom, 0.dp)
                    }
                },
                progress = computedProgress
            ) {
                Box(
                    modifier = Modifier
                        .background(Color.Transparent)
                        .layoutId("header")
                        .fillMaxWidth()
                        .height(contentHeight)
                )
                Column(
                    modifier = Modifier
                        .layoutId("body")
                        .fillMaxWidth()
                        .background(
                            color = Gray000,
                            shape = when (swipeState.progress.to) {
                                HistoryViewSwipingType.COLLAPSED -> RoundedCornerShape(
                                    topStart = 16.dp,
                                    topEnd = 16.dp
                                )

                                HistoryViewSwipingType.EXPANDED -> RoundedCornerShape(
                                    topStart = 0.dp,
                                    topEnd = 0.dp
                                )
                            }
                        )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(searchBoxHeightState.value)
                            .padding(
                                bottom = 2.dp,
                                start = 20.dp,
                                end = 20.dp
                            ),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        val currentColorState = animateColorAsState(
                            targetValue = if (isTextFieldFocused) Primary4 else Color.Transparent,
                            label = "color state"
                        )
                        Surface(
                            shape = RoundedCornerShape(100.dp),
                            border = BorderStroke(
                                width = 1.dp,
                                color = currentColorState.value
                            ),
                            modifier = Modifier.height(45.dp),
                        ) {
                            TypingTextField(
                                textType = TypingTextFieldType.Basic,
                                text = searchText,
                                backgroundColor = Gray200,
                                onValueChange = {
                                    searchText = it
                                },
                                fieldHeight = 45.dp,
                                contentPadding = PaddingValues(
                                    start = if (!isTextFieldFocused && searchText.isEmpty()) 6.dp else 16.dp,
                                ),
                                cursorColor = Primary4,
                                basicBorderColor = Color.Transparent,
                                hintText = "이름을 입력하세요.",
                                hintTextColor = Gray500,
                                leadingIconContent = {
                                    if (!isTextFieldFocused && searchText.isEmpty()) {
                                        Box(modifier = Modifier.padding(start = 16.dp)) {
                                            Image(
                                                painter = painterResource(R.drawable.ic_search),
                                                contentDescription = null,
                                                modifier = Modifier.size(16.dp),
                                                colorFilter = ColorFilter.tint(Gray500)
                                            )
                                        }
                                    }
                                },
                                trailingIconContent = if (searchText.isNotEmpty()) {
                                    {
                                        Image(
                                            painter = painterResource(R.drawable.ic_close_circle_gray),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .size(20.dp)
                                                .clickable {
                                                    searchText = ""
                                                },
                                        )
                                    }
                                } else null,
                                onTextFieldFocusChange = {
                                    isTextFieldFocused = it
                                }
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        TabRow(
                            selectedTabIndex = pagerState.currentPage,
                            backgroundColor = Color.White,
                            contentColor = Primary4,
                            modifier = Modifier
                                .background(
                                    Color.White,
                                    shape = RoundedCornerShape(
                                        topStart = 16.dp,
                                        topEnd = 16.dp
                                    )
                                )
                                .padding(horizontal = 20.dp),
                            divider = {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(Color.Transparent)
                                )
                            }
                        ) {
                            pages.forEachIndexed { index, pageText ->
                                Tab(
                                    selected = index == pagerState.currentPage,
                                    onClick = {
                                        scope.launch {
                                            pagerState.animateScrollToPage(index)
                                        }
                                    },
                                    text = {
                                        Text(
                                            text = pageText,
                                            style = Headline3.merge(
                                                color = if (index == pagerState.currentPage) Primary4 else Gray500,
                                                fontWeight = FontWeight.SemiBold
                                            )
                                        )
                                    }
                                )
                            }
                        }
                    }
                    HorizontalPager(
                        state = pagerState,
                        userScrollEnabled = false
                    ) { pageIndex ->
                        val viewType = HistoryViewType.entries.getOrNull(pageIndex)
                        HistoryPageScreen(
                            appState = appState,
                            model = model,
                            event = event,
                            intent = intent,
                            handler = handler,
                            viewType = viewType ?: HistoryViewType.TOTAL,
                            searchText = searchText
                        )
                    }
                }
            }

            var isDropDownMenuExpanded by remember { mutableStateOf(false) }
            FloatingActionButton(
                modifier = Modifier
                    .padding(bottom = 24.dp, end = 20.dp)
                    .size(52.dp)
                    .align(Alignment.BottomEnd),
                backgroundColor = if (isDropDownMenuExpanded) Gray000 else Gray800,
                onClick = {
                    isDropDownMenuExpanded = true
                }
            ) {
                if (isDropDownMenuExpanded) {
                    Icon(
                        modifier = Modifier.size(32.dp),
                        painter = painterResource(id = R.drawable.ic_close),
                        contentDescription = null,
                        tint = Gray800
                    )
                } else {
                    Icon(
                        modifier = Modifier.size(32.dp),
                        painter = painterResource(id = R.drawable.ic_plus),
                        contentDescription = null,
                        tint = Gray000
                    )
                }

                val isNotEmptyRelation = model.groups.isNotEmpty()

                MaterialTheme(shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(100.dp))) {
                    DropdownMenu(
                        modifier = Modifier
                            .border(
                                width = 1.dp,
                                shape = RoundedCornerShape(100.dp),
                                color = if (isNotEmptyRelation) Gray300 else Gray500
                            )
                            .background(
                                color = if (isNotEmptyRelation) Gray000 else Gray500,
                                shape = RoundedCornerShape(100.dp)
                            )
                            .width(120.dp)
                            .height(48.dp),
                        onDismissRequest = {
                            isDropDownMenuExpanded = false
                        },
                        offset = DpOffset(y = 76.dp, x = 0.dp),
                        expanded = isDropDownMenuExpanded
                    ) {
                        DropdownMenuItem(
                            contentPadding = PaddingValues(bottom = 12.dp),
                            onClick = {
                                if (isNotEmptyRelation) {
                                    isDropDownMenuExpanded = false
                                    appState.navController.navigate(HistoryRegistrationConstant.ROUTE)
                                }
                            },
                            modifier = Modifier.height(48.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxSize(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                            ) {
                                val iconResource =
                                    if (isNotEmptyRelation) R.drawable.ic_gift_activate else R.drawable.ic_gift_disabled
                                Image(
                                    painter = painterResource(iconResource),
                                    contentDescription = null,
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "마음 등록",
                                    style = Headline3.merge(
                                        color = if (isNotEmptyRelation) Primary3 else Gray600,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                )
                            }
                        }
                    }
                }

                MaterialTheme(shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(100.dp))) {
                    DropdownMenu(
                        modifier = Modifier
                            .border(
                                width = 1.dp,
                                shape = RoundedCornerShape(100.dp),
                                color = if (isNotEmptyRelation) Gray300 else Gray500
                            )
                            .background(
                                color = if (isNotEmptyRelation) Gray000 else Gray500,
                                shape = RoundedCornerShape(100.dp)
                            )
                            .width(120.dp)
                            .height(48.dp),
                        onDismissRequest = {
                            isDropDownMenuExpanded = false
                        },
                        offset = DpOffset(y = 16.dp, x = 0.dp),
                        expanded = isDropDownMenuExpanded
                    ) {
                        DropdownMenuItem(
                            contentPadding = PaddingValues(bottom = 12.dp),
                            onClick = {
                                if (isNotEmptyRelation) {
                                    isDropDownMenuExpanded = false
                                    appState.navController.navigate(RelationConstant.ROUTE)
                                }
                            },
                            modifier = Modifier.height(48.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxSize(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.ic_person),
                                    colorFilter = ColorFilter.tint(Secondary5),
                                    modifier = Modifier.size(24.dp),
                                    contentDescription = null,
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "관계 등록",
                                    style = Headline3.merge(
                                        color = Secondary6,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    LaunchedEffectWithLifecycle(event, handler) {
        event.eventObserve { event ->

        }
    }
}

@Composable
@Preview(apiLevel = 33)
private fun HistoryScreenPreview() {
    HistoryScreen(
        appState = rememberApplicationState(),
        model = HistoryModel(
            state = HistoryState.Init,
            groups = listOf(
                GroupWithRelationDetail(
                    id = 0,
                    name = "전체",
                    relationList = listOf(
                        RelationDetail(
                            id = 0,
                            name = "이름",
                            group = RelationDetailGroup(
                                id = 0,
                                name = "친구"
                            ),
                            giveMoney = 10000,
                            takeMoney = 10000
                        )
                    )
                ),
                GroupWithRelationDetail(
                    id = 1,
                    name = "친구",
                    relationList = listOf(
                        RelationDetail(
                            id = 0,
                            name = "이름",
                            group = RelationDetailGroup(
                                id = 0,
                                name = "친구"
                            ),
                            giveMoney = 10000,
                            takeMoney = 10000
                        )
                    )
                ),
                GroupWithRelationDetail(
                    id = 1,
                    name = "가족",
                    relationList = listOf(
                        RelationDetail(
                            id = 0,
                            name = "이름",
                            group = RelationDetailGroup(
                                id = 0,
                                name = "친구"
                            ),
                            giveMoney = 10000,
                            takeMoney = 10000
                        )
                    )
                ),
                GroupWithRelationDetail(
                    id = 1,
                    name = "지인",
                    relationList = listOf(
                        RelationDetail(
                            id = 0,
                            name = "이름",
                            group = RelationDetailGroup(
                                id = 0,
                                name = "친구"
                            ),
                            giveMoney = 10000,
                            takeMoney = 10000
                        )
                    )
                ),
                GroupWithRelationDetail(
                    id = 1,
                    name = "직장",
                    relationList = listOf(
                        RelationDetail(
                            id = 0,
                            name = "이름",
                            group = RelationDetailGroup(
                                id = 0,
                                name = "친구"
                            ),
                            giveMoney = 10000,
                            takeMoney = 10000
                        )
                    )
                ),
                GroupWithRelationDetail(
                    id = 1,
                    name = "지인2",
                    relationList = listOf(
                        RelationDetail(
                            id = 0,
                            name = "이름",
                            group = RelationDetailGroup(
                                id = 0,
                                name = "친구"
                            ),
                            giveMoney = 10000,
                            takeMoney = 10000
                        )
                    )
                )
            ),
            unrecordedSchedule = listOf(
                UnrecordedSchedule(
                    1,
                    UnrecordedScheduleRelation(
                        0,
                        "김진우",
                        UnrecordedScheduleRelationGroup(
                            0,
                            "가족"
                        )
                    ),
                    day = LocalDate(2024, 2, 2),
                    event = "돌잔치",
                    time = LocalTime(12, 12),
                    link = "",
                    location = ""
                ),
            )
        ),
        event = MutableEventFlow(),
        intent = {},
        handler = CoroutineExceptionHandler { _, _ -> }
    )
}
