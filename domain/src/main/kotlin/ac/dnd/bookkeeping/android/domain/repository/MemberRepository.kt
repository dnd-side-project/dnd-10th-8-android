package ac.dnd.bookkeeping.android.domain.repository

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
}
