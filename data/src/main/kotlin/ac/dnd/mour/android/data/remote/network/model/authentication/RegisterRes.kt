package ac.dnd.mour.android.data.remote.network.model.authentication

import ac.dnd.mour.android.data.remote.mapper.DataMapper
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterRes(
    @SerialName("id")
    val id: Long,
    @SerialName("accessToken")
    val accessToken: String,
    @SerialName("refreshToken")
    val refreshToken: String
) : DataMapper<Long> {
    override fun toDomain(): Long {
        return id
    }
}
