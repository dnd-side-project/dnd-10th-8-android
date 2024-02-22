package ac.dnd.mour.android.data.remote.network.api

import ac.dnd.mour.android.data.remote.network.di.AuthHttpClient
import ac.dnd.mour.android.data.remote.network.di.NoAuthHttpClient
import ac.dnd.mour.android.data.remote.network.environment.BaseUrlProvider
import ac.dnd.mour.android.data.remote.network.environment.ErrorMessageMapper
import ac.dnd.mour.android.data.remote.network.model.member.CheckNicknameRes
import ac.dnd.mour.android.data.remote.network.model.member.EditProfileReq
import ac.dnd.mour.android.data.remote.network.model.member.GetProfileRes
import ac.dnd.mour.android.data.remote.network.util.convert
import ac.dnd.mour.android.data.remote.network.util.parameterFiltered
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.setBody
import javax.inject.Inject
import kotlinx.datetime.LocalDate

class MemberApi @Inject constructor(
    @NoAuthHttpClient private val noAuthClient: HttpClient,
    @AuthHttpClient private val client: HttpClient,
    private val baseUrlProvider: BaseUrlProvider,
    private val errorMessageMapper: ErrorMessageMapper
) {
    private val baseUrl: String
        get() = baseUrlProvider.get()

    suspend fun checkNickname(
        nickname: String
    ): Result<CheckNicknameRes> {
        return noAuthClient.get("$baseUrl/api/v1/members/check-nickname") {
            parameterFiltered("nickname", nickname)
        }.convert(errorMessageMapper::map)
    }

    suspend fun editProfile(
        profileImageUrl: String,
        nickname: String,
        gender: String,
        birth: LocalDate
    ): Result<Unit> {
        return client.patch("$baseUrl/api/v1/members/me") {
            setBody(
                EditProfileReq(
                    profileImageUrl = profileImageUrl,
                    nickname = nickname,
                    gender = gender,
                    birth = birth
                )
            )
        }.convert(errorMessageMapper::map)
    }

    suspend fun getProfile(): Result<GetProfileRes> {
        return client.get("$baseUrl/api/v1/members/me")
            .convert(errorMessageMapper::map)
    }
}
