package ac.dnd.mour.android.data.repository.member

import ac.dnd.mour.android.data.remote.local.SharedPreferencesManager
import ac.dnd.mour.android.data.remote.network.api.MemberApi
import ac.dnd.mour.android.data.remote.network.util.toDomain
import ac.dnd.mour.android.domain.model.member.Profile
import ac.dnd.mour.android.domain.repository.MemberRepository
import javax.inject.Inject
import kotlinx.datetime.LocalDate

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

    override suspend fun editProfile(
        profileImageUrl: String,
        nickname: String,
        gender: String,
        birth: LocalDate
    ): Result<Unit> {
        return memberApi.editProfile(
            profileImageUrl = profileImageUrl,
            nickname = nickname,
            gender = gender,
            birth = birth
        )
    }

    override suspend fun getProfile(): Result<Profile> {
        return memberApi.getProfile().toDomain()
    }
}
