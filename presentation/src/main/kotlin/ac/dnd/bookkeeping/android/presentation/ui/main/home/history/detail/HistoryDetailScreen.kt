package ac.dnd.bookkeeping.android.presentation.ui.main.home.history.detail

import ac.dnd.bookkeeping.android.domain.model.feature.heart.RelatedHeart
import ac.dnd.bookkeeping.android.domain.model.feature.relation.RelationDetailGroup
import ac.dnd.bookkeeping.android.domain.model.feature.relation.RelationDetailWithUserInfo
import ac.dnd.bookkeeping.android.presentation.R
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray000
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray200
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray500
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray700
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray800
import ac.dnd.bookkeeping.android.presentation.common.theme.Headline3
import ac.dnd.bookkeeping.android.presentation.common.util.LaunchedEffectWithLifecycle
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.eventObserve
import ac.dnd.bookkeeping.android.presentation.common.util.makeRoute
import ac.dnd.bookkeeping.android.presentation.model.history.HistoryDetailGrowthType
import ac.dnd.bookkeeping.android.presentation.model.history.HistoryTagType
import ac.dnd.bookkeeping.android.presentation.model.history.HistoryViewSwipingType
import ac.dnd.bookkeeping.android.presentation.model.history.HistoryViewType
import ac.dnd.bookkeeping.android.presentation.model.relation.RelationDetailWithUserInfoModel
import ac.dnd.bookkeeping.android.presentation.model.relation.toUiModel
import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import ac.dnd.bookkeeping.android.presentation.ui.main.home.common.relation.RelationConstant
import ac.dnd.bookkeeping.android.presentation.ui.main.home.history.detail.growth.HistoryDetailGrowthConstant
import ac.dnd.bookkeeping.android.presentation.ui.main.home.history.detail.information.HistoryDetailInformationScreen
import ac.dnd.bookkeeping.android.presentation.ui.main.home.history.registration.HistoryRegistrationConstant
import ac.dnd.bookkeeping.android.presentation.ui.main.rememberApplicationState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
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
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

@OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalMaterialApi::class,
    ExperimentalMotionApi::class
)
@Composable
fun HistoryDetailScreen(
    appState: ApplicationState,
    model: HistoryDetailModel,
    event: EventFlow<HistoryDetailEvent>,
    intent: (HistoryDetailIntent) -> Unit,
    handler: CoroutineExceptionHandler
) {
    val scope = rememberCoroutineScope()
    val swipeState = rememberSwipeableState(initialValue = HistoryViewSwipingType.COLLAPSED)
    val backgroundHeight = 430.dp
    val contentHeight = 480.dp
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

    val currentGrowthType = HistoryDetailGrowthType.getGrowthType(
        model.relationDetail.takeMoney + model.relationDetail.giveMoney
    )
    val pages = listOf("전체", "받은 마음", "보낸 마음")
    val pagerState = rememberPagerState(
        pageCount = { 3 }
    )
    var isShowingRelationInfo by remember { mutableStateOf(false) }

    fun navigateToGrowth(money: Long) {
        val route = makeRoute(
            HistoryDetailGrowthConstant.ROUTE,
            listOf(HistoryDetailGrowthConstant.ROUTE_ARGUMENT_TOTAL_MONEY to money)
        )
        appState.navController.navigate(route)
    }

    fun navigateToAddHeart() {
        appState.navController.navigate(HistoryRegistrationConstant.ROUTE)
    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray000)
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
            HistoryDetailBackgroundComponent(
                currentGrowthType = currentGrowthType,
                model = model,
                contentHeight = contentHeight,
                onClickRelationInfo = {
                    isShowingRelationInfo = true
                },
                onClickGrowthInfo = {
                    navigateToGrowth(model.relationDetail.giveMoney + model.relationDetail.takeMoney)
                },
                onClickBack = {
                    appState.navController.popBackStack()
                }
            )
            MotionLayout(
                modifier = Modifier.fillMaxSize(),
                start = ConstraintSet {
                    val header = createRefFor("header")
                    val body = createRefFor("body")
                    constrain(header) {
                        this.width = Dimension.matchParent
                        this.height = Dimension.value(backgroundHeight)
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
                        .height(backgroundHeight)
                )

                Column(
                    modifier = Modifier
                        .layoutId("body")
                        .fillMaxWidth()
                        .background(
                            color = Gray200,
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
                            .height(50.dp)
                    ) {
                        TabRow(
                            selectedTabIndex = pagerState.currentPage,
                            backgroundColor = Color.White,
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
                                                color = if (index == pagerState.currentPage) Gray700 else Gray500,
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
                        HistoryDetailPageScreen(
                            appState = appState,
                            model = model,
                            event = event,
                            intent = intent,
                            handler = handler,
                            viewType = viewType ?: HistoryViewType.TOTAL
                        )
                    }
                }
            }

            FloatingActionButton(
                modifier = Modifier
                    .padding(bottom = 24.dp, end = 20.dp)
                    .size(52.dp)
                    .align(Alignment.BottomEnd),
                backgroundColor = Gray800,
                onClick = {
                    navigateToAddHeart()
                }
            ) {
                Icon(
                    modifier = Modifier.size(32.dp),
                    painter = painterResource(id = R.drawable.ic_plus),
                    contentDescription = null,
                    tint = Gray000
                )
            }
        }
    }

    if (isShowingRelationInfo) {
        HistoryDetailInformationScreen(
            appState = appState,
            relationDetail = model.relationDetail,
            onDismissRequest = {
                isShowingRelationInfo = false
            },
            onResult = {
                appState.navController.navigateToRelation(model.relationDetail.toUiModel())
            }
        )
    }


    LaunchedEffectWithLifecycle(event, handler) {
        event.eventObserve { event ->

        }
    }
}

private fun NavHostController.navigateToRelation(
    relationDetailWithUserInfoModel: RelationDetailWithUserInfoModel
) {
    currentBackStackEntry?.savedStateHandle?.apply {
        set(
            key = RelationConstant.ROUTE_ARGUMENT_MODEL,
            value = relationDetailWithUserInfoModel
        )
    }
    navigate(RelationConstant.CONTAIN_RELATION)
}

@Preview
@Composable
fun HistoryDetailScreenPreview() {
    HistoryDetailScreen(
        appState = rememberApplicationState(),
        model = HistoryDetailModel(
            state = HistoryDetailState.Init,
            relationDetail = RelationDetailWithUserInfo(
                id = 0L,
                name = "김진우",
                imageUrl = "",
                memo = "무르는 경사비 관리앱으로 사용자가 다양한 개인적인 축하 상황에 대해 금전적 기여를 쉽게 할 수 있게 돕는 모바일 애플리케이션입니다",
                group = RelationDetailGroup(
                    id = 0,
                    name = "친척"
                ),
                giveMoney = 100000L,
                takeMoney = 100000L
            ),
            hearts = listOf(
                RelatedHeart(
                    id = 0,
                    give = true,
                    money = 10000,
                    day = LocalDate(2024, 1, 23),
                    event = "생일",
                    memo = "메모 내용 한 줄 넘어가면 숨김 처리",
                    tags = listOf()
                ),
                RelatedHeart(
                    id = 0,
                    give = true,
                    money = 10000,
                    day = LocalDate(2024, 1, 24),
                    event = "생일",
                    memo = "메모 내용 한 줄 넘어가면 숨김 처리",
                    tags = HistoryTagType.entries.map { it.tagName }
                ),
                RelatedHeart(
                    id = 0,
                    give = false,
                    money = 500000,
                    day = LocalDate(2024, 1, 25),
                    event = "생일",
                    memo = "메모 내용 한 줄 넘어가면 숨김 처리 메모 내용 한 줄 넘어가면 숨김 처리 ",
                    tags = listOf("참석", "현금")
                ),
                RelatedHeart(
                    id = 0,
                    give = false,
                    money = 10000,
                    day = LocalDate(2024, 1, 26),
                    event = "생일",
                    memo = "메모 내용 한 줄 넘어가면 숨김 처리",
                    tags = listOf()
                ),
                RelatedHeart(
                    id = 0,
                    give = true,
                    money = 10000,
                    day = LocalDate(2024, 1, 27),
                    event = "생일",
                    memo = "메모 내용 한 줄 넘어가면 숨김 처리",
                    tags = listOf()
                ),
            )
        ),
        event = MutableEventFlow(),
        intent = {},
        handler = CoroutineExceptionHandler { _, _ -> }
    )
}
