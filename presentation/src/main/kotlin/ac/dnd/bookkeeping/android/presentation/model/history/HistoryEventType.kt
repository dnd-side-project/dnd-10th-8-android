package ac.dnd.bookkeeping.android.presentation.model.history

enum class HistoryEventType(
    val id: Long,
    val eventName: String,
    val typeColor: Long
) {
    MARRIAGE(0, "결혼", 0xFFFF547D),
    BIRTHDAY(1, "생일", 0xFFFF9461),
    CHILDBIRTH(2, "출산", 0xFFFFC524),
    FIRST_BIRTHDAY_PARTY(3, "돌잔치", 0xFF28A7F8),
    OPENING_OF_BUSINESS(4, "개업", 0xFF0DC9BA);

    companion object {
        fun getEventName(id: Long): String {
            return entries.find { it.id == id }?.eventName ?: ""
        }

        fun getEventId(name: String): Long {
            return entries.find { it.eventName == name }?.id ?: -1
        }

        fun getEventTypeColor(name: String): Long {
            return entries.find { it.eventName == name }?.typeColor ?: 0xFFA4A6AA
        }
    }
}
