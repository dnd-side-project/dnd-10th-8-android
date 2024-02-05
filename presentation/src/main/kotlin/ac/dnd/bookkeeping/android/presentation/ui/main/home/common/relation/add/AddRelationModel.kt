package ac.dnd.bookkeeping.android.presentation.ui.main.home.common.relation.add

import ac.dnd.bookkeeping.android.domain.model.feature.group.Group
import androidx.compose.runtime.Immutable

@Immutable
data class AddRelationModel(
    val state : AddRelationState,
    val groups : List<Group>
)
