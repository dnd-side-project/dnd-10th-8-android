package ac.dnd.mour.android.domain.repository

import ac.dnd.mour.android.domain.model.member.Profile
import kotlinx.datetime.LocalDate

interface MemberRepository {
    suspend fun checkNickname(
        nickname: String
    ): Result<Boolean>

    suspend fun editProfile(
        profileImageUrl: String,
        nickname: String,
        gender: String,
        birth: LocalDate
    ): Result<Unit>

    suspend fun getProfile(): Result<Profile>
}
