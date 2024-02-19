package ac.dnd.mour.android.presentation.ui.main.home.common.group.edit

import ac.dnd.mour.android.domain.model.feature.group.Group
import androidx.compose.runtime.Immutable

@Immutable
class EditGroupModel(
    val state: EditGroupState,
    val group: Group
)
