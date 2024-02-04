package ac.dnd.bookkeeping.android.data.remote.network.model.heart

import ac.dnd.bookkeeping.android.data.remote.mapper.DataMapper
import ac.dnd.bookkeeping.android.domain.model.heart.Heart
import ac.dnd.bookkeeping.android.domain.model.heart.HeartGroup
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
    @SerialName("id")
    val id: Long,
    @SerialName("relationId")
    val relationId: Long,
    @SerialName("give")
    val give: Boolean,
    @SerialName("name")
    val name: String,
    @SerialName("group")
    val group: GetHeartItemGroupRes,
    @SerialName("giveHistories")
    val giveHistories: List<Long>,
    @SerialName("takeHistories")
    val takeHistories: List<Long>
) : DataMapper<Heart> {
    override fun toDomain(): Heart {
        return Heart(
            id = id,
            relationId = relationId,
            give = give,
            name = name,
            group = group.toDomain(),
            giveHistories = giveHistories,
            takeHistories = takeHistories
        )
    }
}

@Serializable
data class GetHeartItemGroupRes(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String
) : DataMapper<HeartGroup> {
    override fun toDomain(): HeartGroup {
        return HeartGroup(
            id = id,
            name = name
        )
    }
}

