package ac.dnd.bookkeeping.android.presentation.ui.main.home.history.detail

import ac.dnd.bookkeeping.android.domain.model.feature.relation.RelationDetailWithUserInfo

data class HistoryDetailModel(
    val state: HistoryDetailState,
    val relationDetail: RelationDetailWithUserInfo
)
