package ac.dnd.mour.android.data.remote.network.model.member

import ac.dnd.mour.android.data.remote.mapper.DataMapper
import ac.dnd.mour.android.domain.model.member.Profile
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
            gender = gender,
            birth = birth
        )
    }
}
