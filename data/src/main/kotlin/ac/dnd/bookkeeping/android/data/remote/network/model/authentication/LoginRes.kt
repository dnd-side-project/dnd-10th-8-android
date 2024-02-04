package ac.dnd.bookkeeping.android.data.remote.network.model.authentication

import ac.dnd.bookkeeping.android.data.remote.mapper.DataMapper
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRes(
    @SerialName("isNew")
    val isNew: Boolean,
    @SerialName("accessToken")
    val accessToken: String,
    @SerialName("refreshToken")
    val refreshToken: String
) : DataMapper<Boolean> {
    override fun toDomain(): Boolean {
        return isNew
    }
}
