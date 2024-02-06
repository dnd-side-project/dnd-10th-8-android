package ac.dnd.bookkeeping.android.presentation.model.history

enum class HistoryEventType(
    val id: Long,
    val eventName: String
) {
    MARRIAGE(0, "결혼"),
    BIRTHDAY(1, "생일"),
    CHILDBIRTH(2, "출산"),
    FIRST_BIRTHDAY_PARTY(3, "돌잔치"),
    OPENING_OF_BUSINESS(4, "개업");

    companion object {
        fun getEventName(id: Long): String {
            return entries.find { it.id == id }?.eventName ?: ""
        }

        fun getEventId(name: String): Long {
            return entries.find { it.eventName == name }?.id ?: -1
        }
    }
}
