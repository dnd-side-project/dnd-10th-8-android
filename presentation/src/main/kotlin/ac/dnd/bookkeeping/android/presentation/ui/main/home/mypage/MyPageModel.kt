package ac.dnd.bookkeeping.android.presentation.ui.main.home.mypage

import ac.dnd.bookkeeping.android.domain.model.member.Profile

data class MyPageModel(
    val state: MyPageState,
    val profile: Profile
)
