package ac.dnd.mour.android.presentation.model.mypage

import ac.dnd.mour.android.domain.model.member.Profile
import android.os.Parcelable
import kotlinx.datetime.LocalDate
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class ProfileModel(
    val id: Long,
    val email: String,
    val profileImageUrl: String,
    val name: String,
    val nickname: String,
    val gender: String,
    val birth: @RawValue LocalDate
) : Parcelable

fun Profile.toUiModel(): ProfileModel {
    return ProfileModel(
        id = id,
        email = email,
        profileImageUrl = profileImageUrl,
        name = name,
        nickname = nickname,
        gender = gender,
        birth = birth
    )
}
