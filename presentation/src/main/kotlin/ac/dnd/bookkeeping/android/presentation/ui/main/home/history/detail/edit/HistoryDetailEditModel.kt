package ac.dnd.bookkeeping.android.presentation.ui.main.home.history.detail.edit

import ac.dnd.bookkeeping.android.domain.model.feature.heart.RelatedHeart

data class HistoryDetailEditModel(
    val state: HistoryDetailEditState,
    val relatedHeart: RelatedHeart
)
