package ac.dnd.bookkeeping.android.domain.model.member

import kotlinx.datetime.LocalDate

data class Profile(
    val id: Long,
    val email: String,
    val profileImageUrl: String,
    val name: String,
    val nickname: String,
    val profileImageUrl: String,
    val gender: String,
    val birth: LocalDate
)
