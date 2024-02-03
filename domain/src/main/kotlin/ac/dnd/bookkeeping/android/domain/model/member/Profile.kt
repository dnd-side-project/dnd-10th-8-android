package ac.dnd.bookkeeping.android.domain.model.member

import kotlinx.datetime.LocalDate

data class Profile(
    val name: String,
    val nickname: String,
    val gender: String,
    val birth: LocalDate
)
