package ac.dnd.bookkeeping.android.presentation.ui.main.home.common.relation

sealed interface RelationIntent {
    data class OnClickAdd(
        val groupId: Long,
        val name: String,
        val imageUrl: String,
        val memo: String
    ) : RelationIntent

    data class OnClickEdit(
        val id: Long,
        val groupId: Long,
        val name: String,
        val imageUrl: String,
        val memo: String
    ) : RelationIntent

    data class OnClickDelete(
        val id: Long
    ) : RelationIntent

    data object OnClickLoadFriend : RelationIntent
}
