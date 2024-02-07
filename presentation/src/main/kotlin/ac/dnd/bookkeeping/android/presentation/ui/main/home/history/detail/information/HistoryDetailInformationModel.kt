package ac.dnd.bookkeeping.android.presentation.ui.main.home.history.detail.information

import ac.dnd.bookkeeping.android.domain.model.feature.relation.RelationDetailWithUserInfo

data class HistoryDetailInformationModel(
    val state: HistoryDetailInformationState,
    val relationDetail: RelationDetailWithUserInfo
)
