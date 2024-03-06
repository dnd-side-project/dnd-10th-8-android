package ac.dnd.mour.android.presentation.ui.main.home.mypage.profile

import ac.dnd.mour.android.domain.model.member.Profile

sealed interface MyPageProfileEvent {
    sealed interface LoadProfile : MyPageProfileEvent {
        data class Success(val profile: Profile) : LoadProfile
    }

    sealed interface Edit : MyPageProfileEvent {
        data object Success : Edit
    }

    sealed interface CheckName : MyPageProfileEvent {
        data object Success : CheckName
        data object Fail : CheckName
    }
}
