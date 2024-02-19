package ac.dnd.mour.android.presentation.ui.main.home.history.detail.edit

import ac.dnd.mour.android.domain.model.feature.heart.RelatedHeart

data class HistoryDetailEditModel(
    val state: HistoryDetailEditState,
    val relatedHeart: RelatedHeart
)
