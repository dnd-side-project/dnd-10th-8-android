package ac.dnd.bookkeeping.android.data.repository.member

import ac.dnd.bookkeeping.android.domain.repository.MemberRepository
import kotlinx.coroutines.delay
import kotlinx.datetime.LocalDate
import javax.inject.Inject

class MockMemberRepository @Inject constructor() : MemberRepository {
    override suspend fun checkNickname(
        nickname: String
    ): Result<Boolean> {
        randomShortDelay()
        return Result.success(true)
    }

    override suspend fun editProfile(
        profileImageUrl: String,
        nickname: String,
        gender: String,
        birth: LocalDate
    ): Result<Unit> {
        randomShortDelay()
        return Result.success(Unit)
    }

    private suspend fun randomShortDelay() {
        delay(LongRange(100, 500).random())
    }

    private suspend fun randomLongDelay() {
        delay(LongRange(500, 2000).random())
    }
}
