package ac.dnd.bookkeeping.android.presentation.common.view.calendar

enum class TimePickerType(
    val typeName: String
) {
    AM("오전"),
    PM("오후");

    companion object {
        fun getEntryNames(): List<String> {
            return entries.map { it.typeName }
        }

        fun getTimeType(name: String): TimePickerType {
            return entries.find { it.typeName == name } ?: AM
        }
    }
}
