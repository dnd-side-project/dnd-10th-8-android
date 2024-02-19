package ac.dnd.mour.android.data.remote.network.model.group

import ac.dnd.mour.android.data.remote.mapper.DataMapper
import ac.dnd.mour.android.domain.model.feature.group.Group
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetGroupListRes(
    @SerialName("result")
    val result: List<GetGroupItemRes>
) : DataMapper<List<Group>> {
    override fun toDomain(): List<Group> {
        return result.map { it.toDomain() }
    }
}

@Serializable
data class GetGroupItemRes(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String
) : DataMapper<Group> {
    override fun toDomain(): Group {
        return Group(
            id = id,
            name = name
        )
    }
}
