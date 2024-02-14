package ac.dnd.bookkeeping.android.presentation.model.statistics

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed interface MyStatisticsSegmentType : Parcelable {
    @Parcelize
    data object Monthly : MyStatisticsSegmentType

    @Parcelize
    data object Yearly : MyStatisticsSegmentType
}
