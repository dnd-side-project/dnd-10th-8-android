package ac.dnd.mour.android.presentation.model.history

enum class HistoryRegistrationType(
    val typeName: String
) {
    TAKE("받은 마음"),
    GIVE("보낸 마음");

    companion object {
        fun getHistoryRegistration(type: HistoryRegistrationType): Boolean {
            return when (type) {
                TAKE -> false
                GIVE -> true
            }
        }
    }
}
