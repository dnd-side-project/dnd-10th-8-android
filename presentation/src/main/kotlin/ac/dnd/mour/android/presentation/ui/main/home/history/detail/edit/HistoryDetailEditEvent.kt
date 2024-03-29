package ac.dnd.mour.android.presentation.ui.main.home.history.detail.edit

import ac.dnd.mour.android.domain.model.feature.heart.RelatedHeart

sealed interface HistoryDetailEditEvent {
    sealed interface EditRelatedHeart : HistoryDetailEditEvent {
        data class Success(val relatedHeart: RelatedHeart) : EditRelatedHeart
    }

    sealed interface DeleteRelatedHeart : HistoryDetailEditEvent {
        data object Success : DeleteRelatedHeart
    }
}
