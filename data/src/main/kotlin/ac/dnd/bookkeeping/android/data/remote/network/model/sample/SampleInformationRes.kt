package ac.dnd.bookkeeping.android.data.remote.network.model.bookkeeping

import ac.dnd.bookkeeping.android.data.remote.mapper.DataMapper
import ac.dnd.bookkeeping.android.domain.model.bookkeeping.BookkeepingInformation
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookkeepingInformationRes(
    @SerialName("message")
    val message: LyricsResMessage = LyricsResMessage()
) : DataMapper<BookkeepingInformation> {
    override fun toDomain(): BookkeepingInformation {
        return BookkeepingInformation(
            lyricsId = message.body.lyrics.lyricsId,
            lyricsBody = message.body.lyrics.lyricsBody
        )
    }
}

@Serializable
data class LyricsResMessage(
    @SerialName("body")
    val body: LyricsResBody = LyricsResBody()
)

@Serializable
data class LyricsResBody(
    @SerialName("lyrics")
    val lyrics: LyricsResLyrics = LyricsResLyrics()
)

@Serializable
data class LyricsResLyrics(
    @SerialName("lyrics_id")
    val lyricsId: Int = 0,

    @SerialName("lyrics_body")
    val lyricsBody: String = ""
)
