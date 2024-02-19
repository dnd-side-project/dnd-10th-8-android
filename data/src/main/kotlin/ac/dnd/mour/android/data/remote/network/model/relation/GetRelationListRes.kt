package ac.dnd.mour.android.data.remote.network.model.relation

import ac.dnd.mour.android.data.remote.mapper.DataMapper
import ac.dnd.mour.android.domain.model.feature.relation.RelationSimple
import ac.dnd.mour.android.domain.model.feature.relation.RelationSimpleGroup
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetRelationListRes(
    @SerialName("result")
    val result: List<GetRelationItemRes>
) : DataMapper<List<RelationSimple>> {
    override fun toDomain(): List<RelationSimple> {
        return result.map { it.toDomain() }
    }
}

@Serializable
data class GetRelationItemRes(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String,
    @SerialName("group")
    val group: GetRelationItemGroupRes,
) : DataMapper<RelationSimple> {
    override fun toDomain(): RelationSimple {
        return RelationSimple(
            id = id,
            name = name,
            group = group.toDomain()
        )
    }
}

@Serializable
data class GetRelationItemGroupRes(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String
) : DataMapper<RelationSimpleGroup> {
    override fun toDomain(): RelationSimpleGroup {
        return RelationSimpleGroup(
            id = id,
            name = name
        )
    }
}
