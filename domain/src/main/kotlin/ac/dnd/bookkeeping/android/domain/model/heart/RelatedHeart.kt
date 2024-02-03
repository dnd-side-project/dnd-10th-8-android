package ac.dnd.bookkeeping.android.domain.model.heart

import kotlinx.datetime.LocalDate

data class RelatedHeart(
    val id: Long,
    val give: Boolean,
    val money: Long,
    val day: LocalDate,
    val event: String,
    val memo: String,
    val tags: List<String>
)

