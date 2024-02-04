package ac.dnd.bookkeeping.android.data.remote.network.model.relation

import ac.dnd.bookkeeping.android.data.remote.mapper.DataMapper
import ac.dnd.bookkeeping.android.domain.model.feature.relation.RelationDetail
import ac.dnd.bookkeeping.android.domain.model.feature.relation.RelationDetailGroup
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetRelationRes(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String,
    @SerialName("group")
    val group: GetRelationGroupRes,
    @SerialName("giveMoney")
    val giveMoney: Long,
    @SerialName("takeMoney")
    val takeMoney: Long
) : DataMapper<RelationDetail> {
    override fun toDomain(): RelationDetail {
        return RelationDetail(
            id = id,
            name = name,
            group = group.toDomain(),
            giveMoney = giveMoney,
            takeMoney = takeMoney
        )
    }
}

@Serializable
data class GetRelationGroupRes(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String
) : DataMapper<RelationDetailGroup> {
    override fun toDomain(): RelationDetailGroup {
        return RelationDetailGroup(
            id = id,
            name = name
        )
    }
}
