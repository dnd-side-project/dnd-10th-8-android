package ac.dnd.bookkeeping.android.data.repository.member

import ac.dnd.bookkeeping.android.data.remote.local.SharedPreferencesManager
import ac.dnd.bookkeeping.android.data.remote.network.api.MemberApi
import ac.dnd.bookkeeping.android.domain.repository.MemberRepository
import javax.inject.Inject

class RealMemberRepository @Inject constructor(
    private val memberApi: MemberApi,
    private val sharedPreferencesManager: SharedPreferencesManager
) : MemberRepository {
    override suspend fun checkNickname(
        nickname: String
    ): Result<Boolean> {
        return memberApi.checkNickname(
            nickname = nickname
        ).map { checkNickname ->
            checkNickname.result
        }
    }
}
