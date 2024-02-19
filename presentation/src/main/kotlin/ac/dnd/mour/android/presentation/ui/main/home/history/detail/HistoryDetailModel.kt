package ac.dnd.mour.android.presentation.ui.main.home.history.detail

import ac.dnd.mour.android.domain.model.feature.heart.RelatedHeart
import ac.dnd.mour.android.domain.model.feature.relation.RelationDetailWithUserInfo

data class HistoryDetailModel(
    val state: HistoryDetailState,
    val relationDetail: RelationDetailWithUserInfo,
    val hearts: List<RelatedHeart>
)
