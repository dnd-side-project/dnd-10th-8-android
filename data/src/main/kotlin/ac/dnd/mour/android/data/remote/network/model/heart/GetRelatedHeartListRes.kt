package ac.dnd.mour.android.data.remote.network.model.heart

import ac.dnd.mour.android.data.remote.mapper.DataMapper
import ac.dnd.mour.android.domain.model.feature.heart.RelatedHeart
import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetRelatedHeartListRes(
    @SerialName("result")
    val result: List<GetRelatedHeartItemRes>
) : DataMapper<List<RelatedHeart>> {
    override fun toDomain(): List<RelatedHeart> {
        return result.map { it.toDomain() }
    }
}

@Serializable
data class GetRelatedHeartItemRes(
    @SerialName("id")
    val id: Long,
    @SerialName("give")
    val give: Boolean,
    @SerialName("money")
    val money: Long,
    @SerialName("day")
    val day: LocalDate,
    @SerialName("event")
    val event: String,
    @SerialName("memo")
    val memo: String,
    @SerialName("tags")
    val tags: List<String>
) : DataMapper<RelatedHeart> {
    override fun toDomain(): RelatedHeart {
        return RelatedHeart(
            id = id,
            give = give,
            money = money,
            day = day,
            event = event,
            memo = memo,
            tags = tags
        )
    }
}
