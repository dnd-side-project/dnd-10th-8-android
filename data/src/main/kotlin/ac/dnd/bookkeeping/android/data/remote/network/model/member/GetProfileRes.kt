package ac.dnd.bookkeeping.android.data.remote.network.model.member

import ac.dnd.bookkeeping.android.data.remote.mapper.DataMapper
import ac.dnd.bookkeeping.android.domain.model.member.Profile
import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetProfileRes(
    @SerialName("id")
    val id: Long,
    @SerialName("email")
    val email: String,
    @SerialName("profileImageUrl")
    val profileImageUrl: String,
    @SerialName("name")
    val name: String,
    @SerialName("nickname")
    val nickname: String,
    @SerialName("profileImageUrl")
    val profileImageUrl: String,
    @SerialName("gender")
    val gender: String,
    @SerialName("birth")
    val birth: LocalDate
) : DataMapper<Profile> {
    override fun toDomain(): Profile {
        return Profile(
            id = id,
            email = email,
            profileImageUrl = profileImageUrl,
            name = name,
            nickname = nickname,
            profileImageUrl = profileImageUrl,
            gender = gender,
            birth = birth
        )
    }
}
