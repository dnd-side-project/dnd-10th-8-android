package ac.dnd.mour.android.data.repository.authentication

import ac.dnd.mour.android.data.remote.network.api.AuthenticationApi
import ac.dnd.mour.android.domain.model.legacy.Login
import ac.dnd.mour.android.domain.model.legacy.Register
import ac.dnd.mour.android.domain.repository.AuthenticationRepository
import ac.dnd.mour.android.domain.repository.TokenRepository
import javax.inject.Inject

class RealAuthenticationRepository @Inject constructor(
    private val authenticationApi: AuthenticationApi,
    private val tokenRepository: TokenRepository
) : AuthenticationRepository {

    override suspend fun login(
        socialId: Long,
        email: String,
    ): Result<Login> {
        return authenticationApi.login(
            socialId = socialId,
            email = email,
        ).onSuccess { token ->
            tokenRepository.refreshToken = token.refreshToken
            tokenRepository.accessToken = token.accessToken
        }.map { login ->
            Login(id = login.id)
        }
    }

    override suspend fun logout(): Result<Unit> {
        return authenticationApi.logout()
            .onSuccess {
                tokenRepository.refreshToken = ""
                tokenRepository.accessToken = ""
            }
    }

    override suspend fun register(
        socialId: Long,
        email: String,
        profileImageUrl: String,
        name: String,
        nickname: String,
        gender: String,
        birth: String
    ): Result<Register> {
        return authenticationApi.register(
            socialId = socialId,
            email = email,
            profileImageUrl = profileImageUrl,
            name = name,
            nickname = nickname,
            gender = gender,
            birth = birth
        ).onSuccess { register ->
            tokenRepository.refreshToken = register.refreshToken
            tokenRepository.accessToken = register.accessToken
        }.map { register ->
            Register(id = register.id)
        }
    }

    override suspend fun withdraw(): Result<Unit> {
        return authenticationApi.withdraw()
            .onSuccess {
                tokenRepository.refreshToken = ""
                tokenRepository.accessToken = ""
            }
    }
}
