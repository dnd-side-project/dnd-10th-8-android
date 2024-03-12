package ac.dnd.mour.android.presentation.ui.main.home.common.relation

import ac.dnd.mour.android.domain.model.feature.group.Group
import ac.dnd.mour.android.presentation.R
import ac.dnd.mour.android.presentation.common.theme.Body1
import ac.dnd.mour.android.presentation.common.theme.Body2
import ac.dnd.mour.android.presentation.common.theme.Caption2
import ac.dnd.mour.android.presentation.common.theme.Gray000
import ac.dnd.mour.android.presentation.common.theme.Gray200
import ac.dnd.mour.android.presentation.common.theme.Gray400
import ac.dnd.mour.android.presentation.common.theme.Gray500
import ac.dnd.mour.android.presentation.common.theme.Gray600
import ac.dnd.mour.android.presentation.common.theme.Gray700
import ac.dnd.mour.android.presentation.common.theme.Gray800
import ac.dnd.mour.android.presentation.common.theme.Headline1
import ac.dnd.mour.android.presentation.common.theme.Headline3
import ac.dnd.mour.android.presentation.common.theme.Negative
import ac.dnd.mour.android.presentation.common.theme.Shapes
import ac.dnd.mour.android.presentation.common.theme.Space12
import ac.dnd.mour.android.presentation.common.theme.Space20
import ac.dnd.mour.android.presentation.common.theme.Space24
import ac.dnd.mour.android.presentation.common.theme.Space4
import ac.dnd.mour.android.presentation.common.theme.Space56
import ac.dnd.mour.android.presentation.common.theme.Space8
import ac.dnd.mour.android.presentation.common.theme.Space80
import ac.dnd.mour.android.presentation.common.util.LaunchedEffectWithLifecycle
import ac.dnd.mour.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.mour.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.mour.android.presentation.common.util.coroutine.event.eventObserve
import ac.dnd.mour.android.presentation.common.util.expansion.addFocusCleaner
import ac.dnd.mour.android.presentation.common.util.logevent.LogEventUtil
import ac.dnd.mour.android.presentation.common.util.logevent.viewLogEvent
import ac.dnd.mour.android.presentation.common.util.makeRoute
import ac.dnd.mour.android.presentation.common.view.DialogScreen
import ac.dnd.mour.android.presentation.common.view.SnackBarScreen
import ac.dnd.mour.android.presentation.common.view.chip.ChipItem
import ac.dnd.mour.android.presentation.common.view.chip.ChipType
import ac.dnd.mour.android.presentation.common.view.component.FieldSelectComponent
import ac.dnd.mour.android.presentation.common.view.component.FieldSubject
import ac.dnd.mour.android.presentation.common.view.confirm.ConfirmButton
import ac.dnd.mour.android.presentation.common.view.confirm.ConfirmButtonProperties
import ac.dnd.mour.android.presentation.common.view.confirm.ConfirmButtonSize
import ac.dnd.mour.android.presentation.common.view.confirm.ConfirmButtonType
import ac.dnd.mour.android.presentation.common.view.textfield.TypingTextField
import ac.dnd.mour.android.presentation.common.view.textfield.TypingTextFieldType
import ac.dnd.mour.android.presentation.model.relation.RelationDetailGroupModel
import ac.dnd.mour.android.presentation.model.relation.RelationDetailWithUserInfoModel
import ac.dnd.mour.android.presentation.model.relation.RelationType
import ac.dnd.mour.android.presentation.ui.main.ApplicationState
import ac.dnd.mour.android.presentation.ui.main.common.gallery.GalleryScreen
import ac.dnd.mour.android.presentation.ui.main.home.HomeConstant
import ac.dnd.mour.android.presentation.ui.main.home.common.group.get.GetGroupScreen
import ac.dnd.mour.android.presentation.ui.main.home.history.registration.HistoryRegistrationConstant
import ac.dnd.mour.android.presentation.ui.main.home.history.scaledSp
import ac.dnd.mour.android.presentation.ui.main.rememberApplicationState
import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun RelationScreen(
    relationType: RelationType,
    appState: ApplicationState,
    model: RelationModel,
    event: EventFlow<RelationEvent>,
    intent: (RelationIntent) -> Unit,
    handler: CoroutineExceptionHandler
) {

    LaunchedEffect(Unit) {
        if (relationType == RelationType.ADD) {
            viewLogEvent(
                LogEventUtil.VIEW_RELATION,
                block = {

                }
            )
        }
    }
    appState.setStatusBarColor(Gray000)

    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current
    val lazyScrollState = rememberLazyListState()
    val lazyState = remember { derivedStateOf { lazyScrollState.firstVisibleItemIndex } }
    var isRecordState by remember { mutableStateOf(false) }
    var isNameTypeTyping by remember { mutableStateOf(true) }
    var currentImageUrl by remember { mutableStateOf(model.relationDetail.imageUrl) }
    var currentImageName by remember { mutableStateOf("") }
    var isUserNameInValid by remember { mutableStateOf(false) }
    var currentNameText by remember { mutableStateOf(model.relationDetail.name) }
    var isKakaoNameInValid by remember { mutableStateOf(false) }
    var kakaoNameTextFocus by remember { mutableStateOf(false) }
    var kakaoNameText by remember { mutableStateOf("카카오톡에서 친구 선택") }
    var currentGroupId by remember { mutableLongStateOf(model.relationDetail.group.id) }
    var isMemoTextFocus by remember { mutableStateOf(false) }
    var memoText by remember { mutableStateOf(model.relationDetail.memo) }
    var isShowingTooltip by remember { mutableStateOf(true) }
    var isShowingDeleteDialog by remember { mutableStateOf(false) }
    var isCancelWriteState by remember { mutableStateOf(false) }
    var isShowingGetGroup by remember { mutableStateOf(false) }
    var isShowingGalleryView by remember { mutableStateOf(false) }
    var isShowingNonGroupSnackBar by remember { mutableStateOf(false) }
    var isShowingInvalidSnackBar by remember { mutableStateOf(false) }
    var isShowingNonNameSnackBar by remember { mutableStateOf(false) }

    BackHandler(
        enabled = true,
        onBack = {
            isCancelWriteState = true
        }
    )

    LaunchedEffect(Unit) {
        scope.launch {
            delay(20000L)
            isShowingTooltip = false
        }
    }

    fun applyKakaoFriend(event: RelationEvent.LoadKakaoFriend) {
        when (event) {
            is RelationEvent.LoadKakaoFriend.Success -> {
                currentImageUrl = event.imageUrl
                kakaoNameText = event.name
                currentImageName = ""
            }
        }
    }

    fun addRelation(event: RelationEvent.AddRelation) {
        when (event) {
            is RelationEvent.AddRelation.Success -> {
                if (isRecordState) {
                    val route = makeRoute(
                        HistoryRegistrationConstant.ROUTE,
                        listOf(
                            HistoryRegistrationConstant.ROUTE_ARGUMENT_ID to event.relationId,
                            HistoryRegistrationConstant.ROUTE_ARGUMENT_NAME to event.name,
                            HistoryRegistrationConstant.ROUTE_ARGUMENT_IS_HOME to true
                        )
                    )
                    appState.navController.navigate(route)
                } else {
                    appState.navController.previousBackStackEntry?.savedStateHandle?.set(
                        HomeConstant.ROUTE_ARGUMENT_MESSAGE,
                        "등록이 완료되었습니다.",
                    )
                    appState.navController.popBackStack()
                }
            }
        }
    }

    fun deleteRelation(event: RelationEvent.DeleteRelation) {
        when (event) {
            is RelationEvent.DeleteRelation.Success -> {
                val route = makeRoute(
                    HomeConstant.ROUTE,
                    listOf(HomeConstant.ROUTE_ARGUMENT_MESSAGE to "삭제가 완료되었습니다.")
                )
                appState.navController.navigate(route) {
                    popUpTo(RelationConstant.ROUTE) {
                        inclusive = true
                    }
                }
            }
        }
    }

    fun editRelation(event: RelationEvent.EditRelation) {
        when (event) {
            is RelationEvent.EditRelation.Success -> {
                appState.navController.popBackStack()
            }
        }
    }

    fun checkSubmitState(): Boolean {
        return if (currentGroupId < 0) {
            scope.launch {
                isShowingNonGroupSnackBar = true
                delay(1000L)
                isShowingNonGroupSnackBar = false
            }
            false
        } else if (isNameTypeTyping && currentNameText.isEmpty() || !isNameTypeTyping && kakaoNameText.isEmpty()) {
            scope.launch {
                isShowingNonNameSnackBar = true
                delay(1000L)
                isShowingNonNameSnackBar = false
            }
            false
        } else if (isNameTypeTyping && isUserNameInValid || !isNameTypeTyping && isKakaoNameInValid) {
            scope.launch {
                isShowingInvalidSnackBar = true
                delay(1000L)
                isShowingInvalidSnackBar = false
            }
            false
        } else {
            true
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray000)
            .addFocusCleaner(focusManager)
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(state = scrollState)
                .fillMaxSize()
                .padding(horizontal = Space20)
                .padding(
                    top = when (relationType) {
                        RelationType.ADD -> 96.dp
                        RelationType.EDIT -> 72.2.dp
                    }
                )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Space80),
                contentAlignment = Alignment.Center
            ) {
                Box {
                    Box(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .clip(CircleShape)
                            .border(
                                width = 1.dp,
                                color = Gray400,
                                shape = CircleShape
                            )
                            .clickable {
                                isShowingGalleryView = true
                            }
                            .background(Gray400)
                    ) {
                        if (currentImageUrl.isEmpty()) {
                            Image(
                                painter = painterResource(R.drawable.ic_default_user_image),
                                contentDescription = null
                            )
                        } else {
                            AsyncImage(
                                model = currentImageUrl,
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                    Image(
                        painter = painterResource(R.drawable.ic_camera),
                        contentDescription = null,
                        modifier = Modifier.align(Alignment.BottomEnd)
                    )
                }
            }
            Spacer(
                modifier = Modifier.height(
                    when (relationType) {
                        RelationType.ADD -> 43.2.dp
                        RelationType.EDIT -> 40.dp
                    }
                )
            )

            if (relationType == RelationType.ADD) {
                Box(
                    modifier = Modifier.height(38.dp),
                    contentAlignment = Alignment.BottomStart
                ) {
                    if (isShowingTooltip) {
                        Box(
                            modifier = Modifier
                                .background(Color.Transparent)
                                .fillMaxHeight()
                                .wrapContentWidth()
                                .padding(start = 39.5.dp)
                        ) {
                            Card(
                                backgroundColor = Gray000,
                                shape = RoundedCornerShape(100.dp),
                                elevation = 10.dp,
                                contentColor = Color.Transparent,
                                modifier = Modifier
                                    .height(34.dp)
                                    .align(Alignment.TopCenter)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .height(34.dp)
                                        .padding(horizontal = 18.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "카카오톡에서 자동으로 친구 정보를 가져와요.",
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 12.scaledSp(),
                                        style = Body2.merge(color = Gray800)
                                    )
                                }
                            }
                            Image(
                                painter = painterResource(R.drawable.ic_polygon),
                                modifier = Modifier
                                    .offset(y = (-1).dp)
                                    .align(Alignment.BottomCenter),
                                colorFilter = ColorFilter.tint(Gray000),
                                contentDescription = null,
                            )
                        }
                    }
                    FieldSubject(subject = "이름")
                }
            } else {
                FieldSubject(subject = "이름")
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                ChipItem(
                    chipType = if (isNameTypeTyping) ChipType.MAIN else ChipType.BORDER,
                    chipText = "직접 입력",
                    currentSelectedId = setOf(if (isNameTypeTyping) 0 else 1),
                    chipId = 0,
                    onSelectChip = {
                        isNameTypeTyping = true
                        if (relationType == RelationType.ADD) {
                            viewLogEvent(
                                LogEventUtil.CLICK_DIRECT_INPUT_RELATION,
                                block = {

                                }
                            )
                        }
                    }
                )
                ChipItem(
                    chipType = if (isNameTypeTyping) ChipType.BORDER else ChipType.MAIN,
                    chipText = "카카오톡으로 등록",
                    chipId = 1,
                    currentSelectedId = setOf(if (isNameTypeTyping) 0 else 1),
                    onSelectChip = {
                        if (isNameTypeTyping && kakaoNameText == "카카오톡에서 친구 선택") {
                            intent(RelationIntent.OnClickLoadFriend)
                        }
                        isNameTypeTyping = false
                        if (relationType == RelationType.ADD) {
                            viewLogEvent(
                                LogEventUtil.CLICK_KAKAO_RELATION,
                                block = {

                                }
                            )
                        }
                    }
                )
            }
            Spacer(modifier = Modifier.height(Space12))
            if (isNameTypeTyping) {
                TypingTextField(
                    textType = TypingTextFieldType.Basic,
                    text = currentNameText,
                    onValueChange = {
                        currentNameText = it
                        isUserNameInValid = currentNameText.length > 8
                    },
                    hintText = "닉네임 입력 (8자 이내)",
                    hintTextColor = Gray700,
                    isError = isUserNameInValid,
                    errorMessageContent = {
                        if (isUserNameInValid) {
                            errorMessage()
                        }
                    },
                    trailingIconContent = {
                        if (currentNameText.isNotEmpty()) {
                            if (isUserNameInValid) {
                                Image(
                                    painter = painterResource(R.drawable.ic_warning),
                                    contentDescription = null,
                                    modifier = Modifier.size(Space20)
                                )
                            } else {
                                Image(
                                    painter = painterResource(R.drawable.ic_close_circle),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(20.dp)
                                        .clickable {
                                            currentNameText = ""
                                            isUserNameInValid = false
                                        }
                                )
                            }
                        }
                    },
                )
            } else {
                if (kakaoNameText == "카카오톡에서 친구 선택") {
                    FieldSelectComponent(
                        isSelected = kakaoNameTextFocus,
                        text = kakaoNameText,
                        onClick = {},
                    )
                } else {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Box(
                            modifier = Modifier.weight(1f)
                        ) {
                            TypingTextField(
                                textType = TypingTextFieldType.Basic,
                                text = kakaoNameText,
                                onValueChange = {
                                    kakaoNameText = it
                                    isKakaoNameInValid = it.length > 8
                                },
                                isError = isKakaoNameInValid,
                                isSingleLine = true,
                                onTextFieldFocusChange = {
                                    kakaoNameTextFocus = it
                                },
                                trailingIconContent = {
                                    if (kakaoNameTextFocus) {
                                        if (kakaoNameText.isNotEmpty()) {
                                            if (isKakaoNameInValid) {
                                                Image(
                                                    painter = painterResource(R.drawable.ic_warning),
                                                    contentDescription = null,
                                                    modifier = Modifier.size(Space20)
                                                )
                                            } else {
                                                Image(
                                                    painter = painterResource(R.drawable.ic_close_circle),
                                                    contentDescription = null,
                                                    modifier = Modifier
                                                        .size(20.dp)
                                                        .clickable {
                                                            kakaoNameText = ""
                                                            isKakaoNameInValid = false
                                                        }
                                                )
                                            }
                                        }
                                    } else {
                                        Image(
                                            painter = painterResource(R.drawable.ic_edit),
                                            contentDescription = null
                                        )
                                    }
                                },
                                errorMessageContent = {
                                    if (isKakaoNameInValid) {
                                        errorMessage()
                                    }
                                }
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Box(
                            modifier = Modifier
                                .background(
                                    color = Gray200,
                                    shape = Shapes.medium
                                )
                                .clickable {
                                    intent(RelationIntent.OnClickLoadFriend)
                                }
                                .padding(
                                    vertical = 13.5.dp,
                                    horizontal = 16.dp
                                )
                        ) {
                            Text(
                                text = "재등록",
                                style = Body1.merge(
                                    color = Gray700,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(Space24))

            FieldSubject(subject = "그룹")
            Spacer(modifier = Modifier.height(4.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                LazyRow(
                    state = lazyScrollState,
                    modifier = Modifier.padding(
                        top = 6.dp,
                        bottom = 12.dp
                    ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    items(model.groups) { group ->
                        ChipItem(
                            chipType = ChipType.BORDER,
                            currentSelectedId = setOf(currentGroupId),
                            chipText = group.name,
                            onSelectChip = {
                                currentGroupId = it
                            },
                            chipId = group.id
                        )
                    }
                }
                if (lazyState.value > 0) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .width(56.dp)
                            .fillMaxHeight()
                            .background(
                                Brush.horizontalGradient(
                                    listOf(
                                        Color(0xFFFFFFFF),
                                        Color(0x00FFFFFF),
                                    )
                                )
                            )
                    )
                }
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .width(56.dp)
                        .fillMaxHeight()
                        .background(
                            Brush.horizontalGradient(
                                listOf(
                                    Color(0x00FFFFFF),
                                    Color(0xFFFFFFFF),
                                )
                            )
                        )
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(Shapes.medium)
                    .background(color = Gray200)
                    .clickable {
                        if (relationType == RelationType.ADD) {
                            viewLogEvent(
                                LogEventUtil.CLICK_EDIT_RELATION,
                                block = {

                                }
                            )
                        }
                        isShowingGetGroup = true
                    }
                    .padding(
                        horizontal = Space8,
                        vertical = 5.dp
                    )
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_history_edit),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Gray600)
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = "편집",
                    style = Caption2.merge(
                        color = Gray700,
                        fontWeight = FontWeight.Medium
                    )
                )
            }

            Spacer(modifier = Modifier.height(Space24))
            FieldSubject(
                subject = "메모",
                isViewIcon = false
            )
            Spacer(modifier = Modifier.height(7.dp))
            when (relationType) {
                RelationType.EDIT -> {
                    TypingTextField(
                        text = memoText,
                        onValueChange = {
                            memoText = it
                        },
                        contentPadding = PaddingValues(
                            vertical = 15.dp,
                            horizontal = 16.dp
                        ),
                        fieldHeight = 97.dp,
                        onTextFieldFocusChange = {
                            isMemoTextFocus = it
                        },
                        textType = TypingTextFieldType.Basic,
                        trailingIconContent = {
                            if (isMemoTextFocus) {
                                if (memoText.isNotEmpty()) {
                                    Image(
                                        painter = painterResource(R.drawable.ic_close_circle),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(20.dp)
                                            .clickable {
                                                memoText = ""
                                            }
                                    )
                                }
                            } else {
                                Image(
                                    painter = painterResource(R.drawable.ic_chevron_right),
                                    contentDescription = null,
                                    colorFilter = ColorFilter.tint(Gray500)
                                )
                            }
                        },
                        isSingleLine = false
                    )
                }

                RelationType.ADD -> {
                    TypingTextField(
                        text = memoText,
                        onValueChange = {
                            memoText = it
                        },
                        textType = TypingTextFieldType.LongSentence,
                        hintText = "경사 관련 메모를 입력해주세요"
                    )
                }
            }
            Spacer(modifier = Modifier.height(104.dp))

        }

        Row(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .height(Space56)
                .background(color = Gray000)
                .fillMaxWidth()
                .padding(
                    horizontal = 20.dp,
                    vertical = 13.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.ic_chevron_left),
                contentDescription = null,
                modifier = Modifier.clickable {
                    isCancelWriteState = true
                }
            )
            Spacer(modifier = Modifier.width(Space4))
            Text(
                text = when (relationType) {
                    RelationType.EDIT -> "관계 수정하기"
                    RelationType.ADD -> "관계 등록하기"
                },
                style = Headline1.merge(
                    color = Gray800,
                    fontWeight = FontWeight.SemiBold
                )
            )
        }

        Row(
            modifier = Modifier
                .background(color = Gray000)
                .align(Alignment.BottomCenter)
                .padding(
                    vertical = Space12,
                    horizontal = Space20
                ),
            horizontalArrangement = Arrangement.spacedBy(Space12)
        ) {
            when (relationType) {
                RelationType.EDIT -> {
                    Box(
                        modifier = Modifier
                            .clip(Shapes.large)
                            .border(
                                width = 1.dp,
                                color = Gray400,
                                shape = Shapes.large
                            )
                            .clickable {
                                isShowingDeleteDialog = true
                            }
                            .padding(
                                vertical = 14.dp,
                                horizontal = 24.dp
                            )
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_trash),
                            colorFilter = ColorFilter.tint(Color.Black),
                            contentDescription = null,
                            modifier = Modifier.size(Space24)
                        )
                    }
                    ConfirmButton(
                        modifier = Modifier.weight(1f),
                        properties = ConfirmButtonProperties(
                            size = ConfirmButtonSize.Xlarge,
                            type = ConfirmButtonType.Primary
                        ),
                        onClick = {
                            if (checkSubmitState()) {
                                intent(
                                    if (currentImageName.isEmpty()) {
                                        RelationIntent.OnClickEdit(
                                            id = model.relationDetail.id,
                                            groupId = currentGroupId,
                                            name = if (isNameTypeTyping) currentNameText else kakaoNameText,
                                            imageUrl = currentImageUrl,
                                            memo = memoText
                                        )
                                    } else {
                                        RelationIntent.OnClickEditWithUpload(
                                            id = model.relationDetail.id,
                                            groupId = currentGroupId,
                                            name = if (isNameTypeTyping) currentNameText else kakaoNameText,
                                            imageUrl = currentImageUrl,
                                            fileName = currentImageName,
                                            memo = memoText
                                        )
                                    }
                                )
                            }
                        }
                    ) {
                        Text(
                            text = "저장하기",
                            style = Headline3.merge(
                                color = Gray000,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                }

                RelationType.ADD -> {
                    ConfirmButton(
                        modifier = Modifier.weight(1f),
                        properties = ConfirmButtonProperties(
                            size = ConfirmButtonSize.Xlarge,
                            type = ConfirmButtonType.Secondary
                        ),
                        content = {
                            Text(
                                text = "바로 마음 기록",
                                style = Headline3.merge(
                                    color = Gray800,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        },
                        onClick = {
                            if (checkSubmitState()) {
                                isRecordState = true
                                viewLogEvent(
                                    LogEventUtil.CLICK_MONEY_RELATION,
                                    block = {

                                    }
                                )
                                intent(
                                    if (currentImageName.isEmpty()) {
                                        RelationIntent.OnClickAdd(
                                            groupId = currentGroupId,
                                            name = if (isNameTypeTyping) currentNameText else kakaoNameText,
                                            imageUrl = currentImageUrl,
                                            memo = memoText
                                        )
                                    } else {
                                        RelationIntent.OnClickAddWithUpload(
                                            groupId = currentGroupId,
                                            name = if (isNameTypeTyping) currentNameText else kakaoNameText,
                                            imageUrl = currentImageUrl,
                                            fileName = currentImageName,
                                            memo = memoText
                                        )
                                    }
                                )
                            }
                        }
                    )
                    ConfirmButton(
                        modifier = Modifier.weight(1f),
                        properties = ConfirmButtonProperties(
                            size = ConfirmButtonSize.Xlarge,
                            type = ConfirmButtonType.Primary
                        ),
                        content = {
                            Text(
                                text = "저장하기",
                                style = Headline3.merge(
                                    color = Gray000,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        },
                        onClick = {
                            if (checkSubmitState()) {
                                viewLogEvent(
                                    LogEventUtil.CLICK_SAVE_RELATION,
                                    block = {

                                    }
                                )
                                intent(
                                    if (currentImageName.isEmpty()) {
                                        RelationIntent.OnClickAdd(
                                            groupId = currentGroupId,
                                            name = if (isNameTypeTyping) currentNameText else kakaoNameText,
                                            imageUrl = currentImageUrl,
                                            memo = memoText
                                        )
                                    } else {
                                        RelationIntent.OnClickAddWithUpload(
                                            groupId = currentGroupId,
                                            name = if (isNameTypeTyping) currentNameText else kakaoNameText,
                                            imageUrl = currentImageUrl,
                                            fileName = currentImageName,
                                            memo = memoText
                                        )
                                    }
                                )
                            }
                        }
                    )
                }
            }
        }

        if (isShowingNonGroupSnackBar) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 65.dp)
            ) {
                SnackBarScreen("그룹이 선택되지 않았습니다.")
            }
        }

        if (isShowingNonNameSnackBar) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 65.dp)
            ) {
                SnackBarScreen("이름이 선택되지 않았습니다.")
            }
        }

        if (isShowingInvalidSnackBar) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 65.dp)
            ) {
                SnackBarScreen("이름이 유효하지 않습니다.")
            }
        }
    }

    if (isCancelWriteState) {
        val currentText = when (relationType) {
            RelationType.EDIT -> "수정"
            RelationType.ADD -> "작성"
        }
        Dialog(
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            ),
            onDismissRequest = {
                isCancelWriteState = false
            }
        ) {
            Card(
                backgroundColor = Color.White,
                shape = Shapes.large
            ) {
                Column(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(30.dp))
                    Image(
                        painter = painterResource(R.drawable.ic_alert_triangle_gray),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "페이지를 나가면\n${currentText}중인 내용이 삭제돼요.",
                        style = Body1.merge(
                            color = Gray800,
                            fontWeight = FontWeight.SemiBold
                        ),
                        lineHeight = 25.sp,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    Row(modifier = Modifier.wrapContentSize()) {
                        ConfirmButton(
                            properties = ConfirmButtonProperties(
                                size = ConfirmButtonSize.Large,
                                type = ConfirmButtonType.Secondary
                            ),
                            modifier = Modifier.weight(1f),
                            onClick = {
                                isCancelWriteState = false
                                appState.navController.popBackStack()
                            }
                        ) {
                            Text(
                                text = "나가기",
                                style = Headline3.merge(
                                    color = Gray700,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        ConfirmButton(
                            properties = ConfirmButtonProperties(
                                size = ConfirmButtonSize.Large,
                                type = ConfirmButtonType.Primary
                            ),
                            modifier = Modifier.weight(1f),
                            onClick = {
                                isCancelWriteState = false
                            }
                        ) {
                            Text(
                                text = "계속 $currentText",
                                style = Headline3.merge(
                                    color = Gray000,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }

    if (isShowingGetGroup) {
        GetGroupScreen(
            appState = appState,
            groups = model.groups,
            onDismissRequest = {
                isShowingGetGroup = false
            },
            onResult = {
                currentGroupId = it.id
            },
            onGroupChange = {
                intent(RelationIntent.OnGroupChange)
            }
        )
    }

    if (isShowingGalleryView) {
        GalleryScreen(
            appState = appState,
            onDismissRequest = {
                isShowingGalleryView = false
            },
            onResult = { galleryImage ->
                currentImageUrl = galleryImage.filePath
                currentImageName = galleryImage.name
            }
        )
    }

    if (isShowingDeleteDialog) {
        DialogScreen(
            isCancelable = true,
            message = "관계 내역을 삭제하시겠어요?",
            cancelMessage = "취소",
            confirmMessage = "삭제",
            onCancel = {
                isShowingDeleteDialog = false
            },
            onConfirm = {
                intent(RelationIntent.OnClickDelete(model.relationDetail.id))
                isShowingDeleteDialog = false
            },
            onDismissRequest = {
                isShowingDeleteDialog = false
            }
        )
    }

    LaunchedEffectWithLifecycle(event, handler) {
        event.eventObserve { event ->
            when (event) {
                is RelationEvent.LoadKakaoFriend -> applyKakaoFriend(event)
                is RelationEvent.AddRelation -> addRelation(event)
                is RelationEvent.DeleteRelation -> deleteRelation(event)
                is RelationEvent.EditRelation -> editRelation(event)
            }
        }
    }
}

@Composable
private fun errorMessage() {
    Spacer(modifier = Modifier.height(8.dp))
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.ic_alert_triangle),
            contentDescription = null
        )
        Spacer(Modifier.width(Space4))
        Text(
            text = "8자 이내로 입력해주세요",
            style = Body1.merge(color = Negative)
        )
    }
}

@Preview
@Composable
private fun AddRelationScreen1Preview() {
    RelationScreen(
        relationType = RelationType.ADD,
        appState = rememberApplicationState(),
        model = RelationModel(
            groups = listOf(
                Group(
                    id = 0,
                    name = "친구"
                ),
                Group(
                    id = 0,
                    name = "가족"
                ),
                Group(
                    id = 0,
                    name = "지인"
                ),
                Group(
                    id = 0,
                    name = "직장"
                ),
                Group(
                    id = -1,
                    name = "친척"
                )
            ),
            state = RelationState.Init,
            relationDetail = RelationDetailWithUserInfoModel(
                id = 0L,
                name = "김진우",
                imageUrl = "",
                memo = "무르는 경사비 관리앱으로 사용자가 다양한 개인적인 축하 상황에 대해 금전적 기여를 쉽게 할 수 있게 돕는 모바일 애플리케이션입니다",
                group = RelationDetailGroupModel(
                    id = -1,
                    name = "친척"
                ),
                giveMoney = 1000L,
                takeMoney = 1000L
            )
        ),
        intent = {},
        event = MutableEventFlow(),
        handler = CoroutineExceptionHandler { _, _ -> }
    )
}

@Preview
@Composable
private fun AddRelationScreen2Preview() {
    RelationScreen(
        relationType = RelationType.EDIT,
        appState = rememberApplicationState(),
        model = RelationModel(
            groups = listOf(
                Group(
                    id = 0,
                    name = "친구"
                ),
                Group(
                    id = 0,
                    name = "가족"
                ),
                Group(
                    id = 0,
                    name = "지인"
                ),
                Group(
                    id = 0,
                    name = "직장"
                ),
                Group(
                    id = -1,
                    name = "친척"
                )
            ),
            state = RelationState.Init,
            relationDetail = RelationDetailWithUserInfoModel(
                id = 0L,
                name = "김진우",
                imageUrl = "",
                memo = "무르는 경사비 관리앱으로 사용자가 다양한 개인적인 축하 상황에 대해 금전적 기여를 쉽게 할 수 있게 돕는 모바일 애플리케이션입니다",
                group = RelationDetailGroupModel(
                    id = -1,
                    name = "친척"
                ),
                giveMoney = 1000L,
                takeMoney = 1000L
            )
        ),
        intent = {},
        event = MutableEventFlow(),
        handler = CoroutineExceptionHandler { _, _ -> }
    )
}
