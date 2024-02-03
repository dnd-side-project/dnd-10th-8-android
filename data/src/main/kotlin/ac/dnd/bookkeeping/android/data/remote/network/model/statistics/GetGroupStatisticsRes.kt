package ac.dnd.bookkeeping.android.data.remote.network.model.statistics

import ac.dnd.bookkeeping.android.data.remote.mapper.DataMapper
import ac.dnd.bookkeeping.android.domain.model.statistics.GroupStatistics
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetGroupStatisticsRes(
    @SerialName("marriage")
    val marriage: Long,
    @SerialName("birth")
    val birth: Long,
    @SerialName("baby")
    val baby: Long,
    @SerialName("babyBirth")
    val babyBirth: Long,
    @SerialName("bizopen")
    val bizopen: Long,
    @SerialName("etc")
    val etc: Long,
) : DataMapper<GroupStatistics> {
    override fun toDomain(): GroupStatistics {
        return GroupStatistics(
            marriage = marriage,
            birth = birth,
            baby = baby,
            babyBirth = babyBirth,
            bizopen = bizopen,
            etc = etc
        )
    }
}
