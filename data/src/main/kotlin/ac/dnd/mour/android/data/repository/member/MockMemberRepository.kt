package ac.dnd.mour.android.data.repository.member

import ac.dnd.mour.android.domain.model.member.Profile
import ac.dnd.mour.android.domain.repository.MemberRepository
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.datetime.LocalDate

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
                id = 0L,
                email = "hello@gmail.com",
                profileImageUrl = "https://avatars.githubusercontent.com/u/71167956",
                name = "김진우",
                nickname = "진우에몽",
                gender = "male",
                birth = LocalDate(2000, 1, 1)
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
