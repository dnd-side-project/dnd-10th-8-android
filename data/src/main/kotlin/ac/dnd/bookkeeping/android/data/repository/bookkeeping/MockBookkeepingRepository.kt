package ac.dnd.bookkeeping.android.data.repository.bookkeeping

import ac.dnd.bookkeeping.android.domain.model.bookkeeping.BookkeepingInformation
import ac.dnd.bookkeeping.android.domain.repository.BookkeepingRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class MockBookkeepingRepository @Inject constructor() : BookkeepingRepository {
    override suspend fun getBookkeepingInformation(
        apiKey: String,
        title: String,
        artist: String
    ): Result<BookkeepingInformation> {
        randomShortDelay()
        return Result.success(BookkeepingInformation())
    }

    private suspend fun randomShortDelay() {
        delay(LongRange(100, 500).random())
    }

    private suspend fun randomLongDelay() {
        delay(LongRange(500, 2000).random())
    }
}
