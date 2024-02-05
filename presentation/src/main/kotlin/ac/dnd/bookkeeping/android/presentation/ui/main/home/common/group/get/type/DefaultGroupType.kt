package ac.dnd.bookkeeping.android.presentation.ui.main.home.common.group.get.type

enum class DefaultGroupType(
    private val typeName: String
) {
    FRIEND("친구"),
    FAMILY("가족"),
    ACQUAINTANCE("지인"),
    RECTAL("직장");

    companion object {
        fun checkEditable(name: String): Boolean {
            return entries.find { it.typeName == name } == null
        }
    }
}
