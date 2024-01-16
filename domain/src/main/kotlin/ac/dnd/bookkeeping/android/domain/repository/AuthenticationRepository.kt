package ac.dnd.bookkeeping.android.domain.repository

interface AuthenticationRepository {

    var refreshToken: String

    var accessToken: String

    suspend fun getAccessToken(
        refreshToken: String
    ): Result<String>
}
