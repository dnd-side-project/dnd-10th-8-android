package ac.dnd.bookkeeping.android.domain.model.authentication

data class JwtToken(
    val accessToken: String,
    val refreshToken: String
)
