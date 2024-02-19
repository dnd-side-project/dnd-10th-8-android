package ac.dnd.mour.android.presentation.ui.main.home.mypage

import ac.dnd.mour.android.domain.model.member.Profile

data class MyPageModel(
    val state: MyPageState,
    val profile: Profile
)
