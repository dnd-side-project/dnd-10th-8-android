package ac.dnd.mour.android.data.remote.network.model.relation

import ac.dnd.mour.android.data.remote.mapper.DataMapper
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddRelationRes(
    @SerialName("result")
    val result: Long
) : DataMapper<Long> {
    override fun toDomain(): Long {
        return result
    }
}
