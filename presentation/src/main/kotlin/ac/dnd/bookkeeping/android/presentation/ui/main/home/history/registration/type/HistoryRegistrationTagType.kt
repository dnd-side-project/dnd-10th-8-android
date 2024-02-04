package ac.dnd.bookkeeping.android.presentation.ui.main.home.history.registration.type

enum class HistoryRegistrationTagType(
    val id: Long,
    val tagName: String
) {
    ATTEND(0,"참석"),
    ACCOUNT(1,"계좌"),
    GIFT_IN_RETURN(2,"답례품"),
    CASH(3, "현금"),
    NONAPPEARANCE(4,"불참"),
    GIFT_CARD(5,"상품권"),
    WREATH(6,"화환");

    companion object {
        fun getTagName(id:Long): String {
            return entries.find {it.id == id}?.tagName ?: ""
        }
    }
}
