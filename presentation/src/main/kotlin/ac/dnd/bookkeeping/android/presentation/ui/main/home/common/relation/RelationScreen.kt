package ac.dnd.bookkeeping.android.presentation.ui.main.home.common.relation

import ac.dnd.bookkeeping.android.domain.model.feature.group.Group
import ac.dnd.bookkeeping.android.presentation.R
import ac.dnd.bookkeeping.android.presentation.common.theme.Body1
import ac.dnd.bookkeeping.android.presentation.common.theme.Caption2
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray000
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray200
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray400
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray500
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray600
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray700
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray800
import ac.dnd.bookkeeping.android.presentation.common.theme.Headline1
import ac.dnd.bookkeeping.android.presentation.common.theme.Headline3
import ac.dnd.bookkeeping.android.presentation.common.theme.Negative
import ac.dnd.bookkeeping.android.presentation.common.theme.Shapes
import ac.dnd.bookkeeping.android.presentation.common.theme.Space12
import ac.dnd.bookkeeping.android.presentation.common.theme.Space20
import ac.dnd.bookkeeping.android.presentation.common.theme.Space24
import ac.dnd.bookkeeping.android.presentation.common.theme.Space4
import ac.dnd.bookkeeping.android.presentation.common.theme.Space56
import ac.dnd.bookkeeping.android.presentation.common.theme.Space8
import ac.dnd.bookkeeping.android.presentation.common.theme.Space80
import ac.dnd.bookkeeping.android.presentation.common.util.LaunchedEffectWithLifecycle
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.EventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.MutableEventFlow
import ac.dnd.bookkeeping.android.presentation.common.util.coroutine.event.eventObserve
import ac.dnd.bookkeeping.android.presentation.common.util.expansion.addFocusCleaner
import ac.dnd.bookkeeping.android.presentation.common.view.DialogScreen
import ac.dnd.bookkeeping.android.presentation.common.view.chip.ChipItem
import ac.dnd.bookkeeping.android.presentation.common.view.chip.ChipType
import ac.dnd.bookkeeping.android.presentation.common.view.component.FieldSelectComponent
import ac.dnd.bookkeeping.android.presentation.common.view.component.FieldSubject
import ac.dnd.bookkeeping.android.presentation.common.view.confirm.ConfirmButton
import ac.dnd.bookkeeping.android.presentation.common.view.confirm.ConfirmButtonProperties
import ac.dnd.bookkeeping.android.presentation.common.view.confirm.ConfirmButtonSize
import ac.dnd.bookkeeping.android.presentation.common.view.confirm.ConfirmButtonType
import ac.dnd.bookkeeping.android.presentation.common.view.textfield.TypingTextField
import ac.dnd.bookkeeping.android.presentation.common.view.textfield.TypingTextFieldType
import ac.dnd.bookkeeping.android.presentation.model.relation.RelationDetailGroupModel
import ac.dnd.bookkeeping.android.presentation.model.relation.RelationDetailWithUserInfoModel
import ac.dnd.bookkeeping.android.presentation.model.relation.RelationType
import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import ac.dnd.bookkeeping.android.presentation.ui.main.home.common.group.get.GetGroupScreen
import ac.dnd.bookkeeping.android.presentation.ui.main.rememberApplicationState
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlinx.coroutines.CoroutineExceptionHandler

@Composable
fun RelationScreen(
    relationType: RelationType,
    appState: ApplicationState,
    model: RelationModel,
    event: EventFlow<RelationEvent>,
    intent: (RelationIntent) -> Unit,
    handler: CoroutineExceptionHandler
) {
    val focusManager = LocalFocusManager.current
    var isRecordState by remember { mutableStateOf(false) }
    var isNameTypeTyping by remember { mutableStateOf(true) }
    var currentImageUrl by remember { mutableStateOf(model.relationDetail.imageUrl) }
    var isUserNameInValid by remember { mutableStateOf(false) }
    var currentNameText by remember { mutableStateOf(model.relationDetail.name) }
    var isKakaoNameInValid by remember { mutableStateOf(false) }
    var kakaoNameTextFocus by remember { mutableStateOf(false) }
    var kakaoNameText by remember { mutableStateOf("카카오에서 친구 선택") }
    var isShowingKakaoPicker by remember { mutableStateOf(false) }
    var currentGroupId by remember { mutableLongStateOf(model.relationDetail.group.id) }
    var isMemoTextFocus by remember { mutableStateOf(false) }
    var memoText by remember { mutableStateOf(model.relationDetail.memo) }
    var isShowingDeleteDialog by remember { mutableStateOf(false) }
    var isCancelEditState by remember { mutableStateOf(false) }
    var isShowingGetGroup by remember { mutableStateOf(false) }
    BackHandler(
        enabled = true,
        onBack = {
            isCancelEditState = true
        }
    )

    fun applyKakaoFriend(event: RelationEvent.LoadKakaoFriend) {
        when (event) {
            is RelationEvent.LoadKakaoFriend.Success -> {
                currentImageUrl = event.imageUrl
                kakaoNameText = event.name
            }
        }
    }

    fun addRelation(event: RelationEvent.AddRelation) {
        when (event) {
            is RelationEvent.AddRelation.Success -> {
                if (isRecordState) {
                    //TODO go heart record this relation
                } else {
                    //TODO go main
                }
            }
        }
    }

    fun deleteRelation(event: RelationEvent.DeleteRelation) {
        when (event) {
            is RelationEvent.DeleteRelation.Success -> {
                //TODO go main
            }
        }
    }

    fun editRelation(event: RelationEvent.EditRelation) {
        when (event) {
            is RelationEvent.EditRelation.Success -> {
                //TODO go main
            }
        }
    }

    fun checkSubmitState(): Boolean {
        return if (currentGroupId < 0) {
            // TODO 그룹이 선택되지 않았습니다. 스낵바
            false
        } else {
            // TODO 이름이 입력되지 않았습니다. 스낵바
            !(isNameTypeTyping && currentNameText.isEmpty() || !isNameTypeTyping && kakaoNameText.isEmpty())
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray000)
            .addFocusCleaner(focusManager)
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .height(Space56)
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
                    isCancelEditState = true
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = Space20)
                .padding(
                    top = when (relationType) {
                        RelationType.ADD -> 120.dp
                        RelationType.EDIT -> 96.dp
                    }
                )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Space80),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier.clickable {
                        // TODO to gallery
                    }
                ) {
                    Box(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .clip(CircleShape)
                            .background(Gray400)
                    ) {
                        AsyncImage(
                            model = currentImageUrl,
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
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
                        RelationType.ADD -> 60.dp
                        RelationType.EDIT -> 40.dp
                    }
                )
            )
            FieldSubject(subject = "이름")
            Spacer(modifier = Modifier.height(Space12))
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
                    }
                )
                // TODO 툴팁 필요  -> 라이브러리 or 커스텀
                ChipItem(
                    chipType = if (isNameTypeTyping) ChipType.BORDER else ChipType.MAIN,
                    chipText = "카카오톡으로 등록",
                    chipId = 1,
                    currentSelectedId = setOf(if (isNameTypeTyping) 0 else 1),
                    onSelectChip = {
                        isNameTypeTyping = false
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
                        isUserNameInValid = currentNameText.length > 5
                    },
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
                        isSelected = isShowingKakaoPicker,
                        text = kakaoNameText,
                        onClick = {
                            isShowingKakaoPicker = true
                        },
                    )
                } else {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TypingTextField(
                            textType = TypingTextFieldType.Basic,
                            text = kakaoNameText,
                            onValueChange = {
                                currentNameText = it
                                isKakaoNameInValid = it.length > 5
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
                        Spacer(modifier = Modifier.width(12.dp))
                        Box(
                            modifier = Modifier
                                .background(
                                    color = Gray200,
                                    shape = Shapes.medium
                                )
                                .clickable {
                                    isShowingKakaoPicker = true
                                }
                                .padding(
                                    vertical = 13.5.dp,
                                    horizontal = 16.dp
                                )
                        ) {
                            Text(
                                text = "재등록",
                                style = Body1.merge(
                                    color = Gray600,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(Space24))
            FieldSubject(subject = "그룹")
            Spacer(modifier = Modifier.height(6.dp))
            LazyRow(
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
            Spacer(modifier = Modifier.height(Space12))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(Shapes.medium)
                    .background(color = Gray200)
                    .clickable {
                        isShowingGetGroup = true
                    }
                    .padding(
                        horizontal = Space8,
                        vertical = 5.dp
                    )
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_edit),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = "편집",
                    style = Caption2.merge(
                        color = Gray600,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
            Spacer(modifier = Modifier.height(Space24))
            FieldSubject(
                subject = "메모",
                isViewIcon = false
            )
            Spacer(
                modifier = Modifier.height(6.dp)
            )
            when (relationType) {
                RelationType.EDIT -> {
                    TypingTextField(
                        text = memoText,
                        onValueChange = {
                            memoText = it
                        },
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
                        textType = TypingTextFieldType.LongSentence
                    )
                }
            }
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
                                    RelationIntent.OnClickEdit(
                                        id = model.relationDetail.id,
                                        groupId = currentGroupId,
                                        name = if (isNameTypeTyping) currentNameText else kakaoNameText,
                                        imageUrl = currentImageUrl,
                                        memo = memoText
                                    )
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
                                    color = Gray700,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        },
                        onClick = {
                            if (checkSubmitState()) {
                                isRecordState = true
                                intent(
                                    RelationIntent.OnClickAdd(
                                        groupId = currentGroupId,
                                        name = if (isNameTypeTyping) currentNameText else kakaoNameText,
                                        imageUrl = currentImageUrl,
                                        memo = memoText
                                    )
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
                                intent(
                                    RelationIntent.OnClickAdd(
                                        groupId = currentGroupId,
                                        name = if (isNameTypeTyping) currentNameText else kakaoNameText,
                                        imageUrl = currentImageUrl,
                                        memo = memoText
                                    )
                                )
                            }
                        }
                    )
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
                intent(RelationIntent.OnGroupChange(it))
            }
        )
    }

    if (isShowingKakaoPicker) {
        intent(RelationIntent.OnClickLoadFriend)
        kakaoNameTextFocus = false
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
            text = "5자 이내로 입력해주세요",
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
