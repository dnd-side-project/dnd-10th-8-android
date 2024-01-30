package ac.dnd.bookkeeping.android.data.repository.member

import ac.dnd.bookkeeping.android.domain.repository.MemberRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class MockMemberRepository @Inject constructor() : MemberRepository {
    override suspend fun checkNickname(
        nickname: String
    ): Result<Boolean> {
        randomShortDelay()
        return Result.success(true)
    }

    private suspend fun randomShortDelay() {
        delay(LongRange(100, 500).random())
    }

    private suspend fun randomLongDelay() {
        delay(LongRange(500, 2000).random())
    }
}
