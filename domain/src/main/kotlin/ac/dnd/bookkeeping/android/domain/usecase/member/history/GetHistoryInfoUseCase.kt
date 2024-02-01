package ac.dnd.bookkeeping.android.domain.usecase.member.history

import ac.dnd.bookkeeping.android.domain.model.event.Group
import ac.dnd.bookkeeping.android.domain.model.history.HistoryInfo
import javax.inject.Inject

class GetHistoryInfoUseCase @Inject constructor(

) {
    suspend fun invoke(): Result<HistoryInfo> {
        // TODO fix when api update
        return Result.success(
            HistoryInfo(
                unwrittenCount = 5,
                totalHeartCount = 32,
                groups = listOf(
                    Group(
                        1,
                        "전체",
                        listOf()
                    ),
                    Group(
                        2,
                        "친구",
                        listOf()
                    ),
                    Group(
                        3,
                        "가족",
                        listOf()
                    ),
                    Group(
                        4,
                        "지인",
                        listOf()
                    ),
                    Group(
                        5,
                        "직장",
                        listOf()
                    ),
                    Group(
                        6,
                        "사촌",
                        listOf()
                    )
                )
            )
        )
    }
}
