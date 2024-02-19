package ac.dnd.mour.android.data.remote.network.model.member

import ac.dnd.mour.android.data.remote.mapper.DataMapper
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CheckNicknameRes(
    @SerialName("result")
    val result: Boolean
) : DataMapper<Boolean> {
    override fun toDomain(): Boolean {
        return result
    }
}
