package ac.dnd.mour.android.presentation.ui.main.home.history.detail.information

import ac.dnd.mour.android.domain.model.feature.relation.RelationDetailWithUserInfo

data class HistoryDetailInformationModel(
    val state: HistoryDetailInformationState,
    val relationDetail: RelationDetailWithUserInfo
)
