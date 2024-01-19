package ac.dnd.bookkeeping.android.presentation.model.bookkeeping

import ac.dnd.bookkeeping.android.domain.model.bookkeeping.BookkeepingInformation
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BookkeepingInformationModel(
    val lyricsId: Int = 0,
    val lyricsBody: String = ""
) : Parcelable

fun BookkeepingInformation.toUiModel(): BookkeepingInformationModel {
    return BookkeepingInformationModel(lyricsId, lyricsBody)
}
