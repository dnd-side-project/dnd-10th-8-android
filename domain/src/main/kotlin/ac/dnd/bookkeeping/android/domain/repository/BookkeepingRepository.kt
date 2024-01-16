package ac.dnd.bookkeeping.android.domain.repository

import ac.dnd.bookkeeping.android.domain.model.bookkeeping.BookkeepingInformation

interface BookkeepingRepository {
    suspend fun getBookkeepingInformation(
        apiKey: String,
        title: String,
        artist: String
    ): Result<BookkeepingInformation>
}
