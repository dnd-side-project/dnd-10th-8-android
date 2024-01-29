package ac.dnd.bookkeeping.android.domain.repository

interface MemberRepository {
    suspend fun checkNickname(
        nickname: String
    ): Result<Boolean>
}
