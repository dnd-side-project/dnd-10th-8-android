package ac.dnd.bookkeeping.android.presentation.model.bookkeeping

import android.os.Parcelable
import ac.dnd.bookkeeping.android.domain.model.bookkeeping.BookkeepingInformation
import kotlinx.parcelize.Parcelize

@Parcelize
data class BookkeepingInformationModel(
    val lyricsId: Int = 0,
    val lyricsBody: String = ""
) : Parcelable

fun BookkeepingInformation.toUiModel(): BookkeepingInformationModel {
    return BookkeepingInformationModel(lyricsId, lyricsBody)
}
