package ac.dnd.mour.android.presentation.ui.main.home.common.group.edit

sealed interface EditGroupIntent {
    data class OnEdit(
        val groupId: Long,
        val name: String
    ) : EditGroupIntent
}
