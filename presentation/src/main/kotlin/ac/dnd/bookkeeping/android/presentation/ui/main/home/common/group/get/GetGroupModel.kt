package ac.dnd.bookkeeping.android.presentation.ui.main.home.common.group.get

import ac.dnd.bookkeeping.android.domain.model.feature.group.Group
import androidx.compose.runtime.Immutable

@Immutable
data class GetGroupModel(
    val state: GetGroupState,
    val groups: List<Group>
)
