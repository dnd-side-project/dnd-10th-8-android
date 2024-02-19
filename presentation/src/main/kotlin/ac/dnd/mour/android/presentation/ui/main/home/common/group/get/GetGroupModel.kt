package ac.dnd.mour.android.presentation.ui.main.home.common.group.get

import ac.dnd.mour.android.domain.model.feature.group.Group
import androidx.compose.runtime.Immutable

@Immutable
data class GetGroupModel(
    val state: GetGroupState,
    val groups: List<Group>
)
