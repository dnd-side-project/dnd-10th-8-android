package ac.dnd.bookkeeping.android.data.remote.network.model.member

import ac.dnd.bookkeeping.android.data.remote.mapper.DataMapper
import ac.dnd.bookkeeping.android.domain.model.member.Profile
import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetProfileRes(
    @SerialName("name")
    val name: String,
    @SerialName("nickname")
    val nickname: String,
    @SerialName("gender")
    val gender: String,
    @SerialName("birth")
    val birth: LocalDate
) : DataMapper<Profile> {
    override fun toDomain(): Profile {
        return Profile(
            name = name,
            nickname = nickname,
            gender = gender,
            birth = birth
        )
    }
}
