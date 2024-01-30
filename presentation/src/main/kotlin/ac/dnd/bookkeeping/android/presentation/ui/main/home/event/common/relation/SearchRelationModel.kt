package ac.dnd.bookkeeping.android.presentation.ui.main.home.event.common.relation

import ac.dnd.bookkeeping.android.domain.model.event.Group
import androidx.compose.runtime.Immutable

@Immutable
data class SearchRelationModel(
    val state: SearchRelationState,
    val groups: List<Group>
)
