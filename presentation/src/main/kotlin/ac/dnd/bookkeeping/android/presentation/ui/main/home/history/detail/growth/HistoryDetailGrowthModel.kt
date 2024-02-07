package ac.dnd.bookkeeping.android.presentation.ui.main.home.history.detail.growth

import ac.dnd.bookkeeping.android.presentation.model.history.HistoryDetailGrowthType

data class HistoryDetailGrowthModel(
    val state: HistoryDetailGrowthState,
    val currentType: HistoryDetailGrowthType
)
