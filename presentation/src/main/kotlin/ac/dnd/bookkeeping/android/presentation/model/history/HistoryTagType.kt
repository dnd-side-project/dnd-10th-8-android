package ac.dnd.bookkeeping.android.presentation.model.history

enum class HistoryTagType(
    val id: Long,
    val tagName: String
) {
    ATTEND(0, "참석"),
    ACCOUNT(1, "계좌"),
    GIFT_IN_RETURN(2, "답례품"),
    CASH(3, "현금"),
    NONAPPEARANCE(4, "불참"),
    GIFT_CARD(5, "상품권"),
    WREATH(6, "화환");

    companion object {
        fun getTagNameList(idList: List<Long>): List<String> {
            return entries.filter {
                it.id in idList
            }.map {
                it.tagName
            }
        }

        fun getTagIdList(nameList: List<String>): List<Long>{
            return entries.filter {
                it.tagName in nameList
            }.map {
                it.id
            }
        }
    }
}
