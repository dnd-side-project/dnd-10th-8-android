package ac.dnd.mour.android.domain.model.member

import kotlinx.datetime.LocalDate

data class Profile(
    val id: Long,
    val email: String,
    val profileImageUrl: String,
    val name: String,
    val nickname: String,
    val gender: String,
    val birth: LocalDate
)
