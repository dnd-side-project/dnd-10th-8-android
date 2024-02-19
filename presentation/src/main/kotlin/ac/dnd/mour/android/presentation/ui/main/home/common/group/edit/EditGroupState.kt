package ac.dnd.mour.android.presentation.ui.main.home.common.group.edit

sealed interface EditGroupState {
    data object Init : EditGroupState
    data object Loading : EditGroupState
}
