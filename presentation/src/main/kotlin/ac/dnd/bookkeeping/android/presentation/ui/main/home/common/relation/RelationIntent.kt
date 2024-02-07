package ac.dnd.bookkeeping.android.presentation.ui.main.home.common.relation

sealed interface RelationIntent {
    data class OnClickSubmit(
        val groupId: Long,
        val name: String,
        val imageUrl: String,
        val memo: String
    ) : RelationIntent
}
