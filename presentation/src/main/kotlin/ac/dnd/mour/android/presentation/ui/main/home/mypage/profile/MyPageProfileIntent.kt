package ac.dnd.mour.android.presentation.ui.main.home.mypage.profile

import ac.dnd.mour.android.presentation.model.mypage.ProfileModel

sealed interface MyPageProfileIntent {
    data class OnEdit(
        val profile: ProfileModel,
        val imageName: String
    ) : MyPageProfileIntent
}
