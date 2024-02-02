package ac.dnd.bookkeeping.android.domain.usecase.history

import ac.dnd.bookkeeping.android.domain.model.history.HistoryInfo
import javax.inject.Inject

class GetHistoryInfoUseCase @Inject constructor(

) {
    suspend operator fun invoke(): Result<HistoryInfo> {
        // TODO fix when api update
        return Result.success(
            HistoryInfo(
                unWrittenCount = 5,
                totalHeartCount = 32,
                unReadAlarm = false
            )
        )
    }
}
