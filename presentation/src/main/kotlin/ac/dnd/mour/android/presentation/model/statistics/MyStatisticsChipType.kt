package ac.dnd.mour.android.presentation.model.statistics

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed interface MyStatisticsChipType : Parcelable {
    @Parcelize
    data object Take : MyStatisticsChipType

    @Parcelize
    data object Give : MyStatisticsChipType
}
