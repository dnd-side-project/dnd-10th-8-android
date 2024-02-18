package ac.dnd.bookkeeping.android.data.repository.member

import ac.dnd.bookkeeping.android.domain.model.member.Profile
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

    override suspend fun getProfile(): Result<Profile> {
        randomShortDelay()
        return Result.success(
            Profile(
                name = "김진우",
                nickname = "진우에몽",
                gender = "male",
                birth = LocalDate(2000, 1, 1),
                profileImageUrl = ""
            )
        )
    }

    private suspend fun randomShortDelay() {
        delay(LongRange(100, 500).random())
    }

    private suspend fun randomLongDelay() {
        delay(LongRange(500, 2000).random())
    }
}
