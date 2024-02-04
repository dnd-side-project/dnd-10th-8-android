package ac.dnd.bookkeeping.android.data.remote.network.model.heart

import ac.dnd.bookkeeping.android.data.remote.mapper.DataMapper
import ac.dnd.bookkeeping.android.domain.model.feature.heart.Heart
import ac.dnd.bookkeeping.android.domain.model.feature.heart.HeartRelation
import ac.dnd.bookkeeping.android.domain.model.feature.heart.HeartRelationGroup
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetHeartListRes(
    @SerialName("result")
    val result: List<GetHeartItemRes>
) : DataMapper<List<Heart>> {
    override fun toDomain(): List<Heart> {
        return result.map { it.toDomain() }
    }
}

@Serializable
data class GetHeartItemRes(
    @SerialName("relation")
    val relation: GetHeartItemRelationRes,
    @SerialName("giveHistories")
    val giveHistories: List<Long>,
    @SerialName("takeHistories")
    val takeHistories: List<Long>
) : DataMapper<Heart> {
    override fun toDomain(): Heart {
        return Heart(
            relation = relation.toDomain(),
            giveHistories = giveHistories,
            takeHistories = takeHistories
        )
    }
}

@Serializable
data class GetHeartItemRelationRes(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String,
    @SerialName("group")
    val group: GetHeartItemRelationGroupRes
) : DataMapper<HeartRelation> {
    override fun toDomain(): HeartRelation {
        return HeartRelation(
            id = id,
            name = name,
            group = group.toDomain()
        )
    }
}

@Serializable
data class GetHeartItemRelationGroupRes(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String
) : DataMapper<HeartRelationGroup> {
    override fun toDomain(): HeartRelationGroup {
        return HeartRelationGroup(
            id = id,
            name = name
        )
    }
}
