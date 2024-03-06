package ac.dnd.mour.android.presentation.ui.main.home.mypage.profile

import ac.dnd.mour.android.domain.model.member.Profile

sealed interface MyPageProfileIntent {
    data class OnEdit(
        val profile: Profile,
        val imageName: String
    ) : MyPageProfileIntent

    data class CheckName(
        val name: String
    ) : MyPageProfileIntent
}
