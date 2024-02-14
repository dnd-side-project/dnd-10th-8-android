package ac.dnd.bookkeeping.android.presentation.ui.main.home.history.detail

import ac.dnd.bookkeeping.android.domain.model.feature.heart.RelatedHeart

sealed interface HistoryDetailIntent {
    data class OnEdit(val editHeart: RelatedHeart) : HistoryDetailIntent
    data class OnDelete(val deleteId: Long) : HistoryDetailIntent
}
