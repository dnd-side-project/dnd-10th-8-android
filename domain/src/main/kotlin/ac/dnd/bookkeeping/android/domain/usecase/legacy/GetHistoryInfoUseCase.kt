package ac.dnd.bookkeeping.android.domain.usecase.legacy

import ac.dnd.bookkeeping.android.domain.model.legacy.HistoryInfoLegacy
import javax.inject.Inject

class GetHistoryInfoUseCase @Inject constructor(

) {
    suspend operator fun invoke(): Result<HistoryInfoLegacy> {
        // TODO fix when api update
        return Result.success(
            HistoryInfoLegacy(
                unWrittenCount = 5,
                totalHeartCount = 32,
                unReadAlarm = false
            )
        )
    }
}
