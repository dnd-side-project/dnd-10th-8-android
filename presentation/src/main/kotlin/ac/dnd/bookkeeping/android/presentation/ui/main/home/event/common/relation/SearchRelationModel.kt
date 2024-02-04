package ac.dnd.bookkeeping.android.presentation.ui.main.home.event.common.relation

import ac.dnd.bookkeeping.android.domain.model.legacy.GroupLegacy
import androidx.compose.runtime.Immutable

@Immutable
data class SearchRelationModel(
    val state: SearchRelationState,
    val groups: List<GroupLegacy>
)
