package ac.dnd.mour.android.presentation.model.history

import ac.dnd.mour.android.presentation.R
import androidx.annotation.DrawableRes

enum class HistoryEventType(
    val id: Long,
    val eventName: String,
    val typeColor: Long,
    @DrawableRes val iconRes: Int
) {
    MARRIAGE(0, "결혼", 0xFFFF547D, R.drawable.ic_event_marriage),
    BIRTHDAY(1, "생일", 0xFFFF9461, R.drawable.ic_event_birthday),
    CHILDBIRTH(2, "출산", 0xFFFFC524, R.drawable.ic_event_childbirth),
    FIRST_BIRTHDAY_PARTY(3, "돌잔치", 0xFF28A7F8, R.drawable.ic_event_first_birthday_party),
    OPENING_OF_BUSINESS(4, "개업", 0xFF0DC9BA, R.drawable.ic_event_opening_of_business);

    companion object {
        fun getEventName(id: Long): String {
            return entries.find { it.id == id }?.eventName ?: ""
        }

        fun getEventId(name: String): Long {
            return entries.find { it.eventName == name }?.id ?: -1
        }

        fun getEventTypeColor(name: String): Long {
            return entries.find { it.eventName == name }?.typeColor ?: 0xFFE8E9EA
        }

        fun getEventIconRes(name: String): Int {
            return entries.find { it.eventName == name }?.iconRes ?: R.drawable.ic_event_etc
        }
    }
}
