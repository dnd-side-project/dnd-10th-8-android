package ac.dnd.bookkeeping.android.presentation.ui.main.home.common.group.edit

import ac.dnd.bookkeeping.android.domain.model.feature.group.Group
import androidx.compose.runtime.Immutable

@Immutable
class EditGroupModel(
    val state: EditGroupState,
    val group: Group
)
