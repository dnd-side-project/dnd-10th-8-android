package ac.dnd.mour.android.presentation.ui.main.home.mypage.profile

import ac.dnd.mour.android.domain.model.member.Profile

data class MyPageProfileModel(
    val state: MyPageProfileState,
    val profile: Profile
)
