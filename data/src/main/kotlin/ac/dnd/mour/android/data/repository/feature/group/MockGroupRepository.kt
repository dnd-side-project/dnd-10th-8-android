package ac.dnd.mour.android.data.repository.feature.group

import ac.dnd.mour.android.domain.model.feature.group.Group
import ac.dnd.mour.android.domain.repository.GroupRepository
import javax.inject.Inject
import kotlinx.coroutines.delay

class MockGroupRepository @Inject constructor() : GroupRepository {

    override suspend fun addGroup(
        name: String
    ): Result<Long> {
        randomShortDelay()
        return Result.success(-1)
    }

    override suspend fun editGroup(
        id: Long,
        name: String
    ): Result<Unit> {
        randomShortDelay()
        return Result.success(Unit)
    }

    override suspend fun deleteGroup(
        id: Long
    ): Result<Unit> {
        randomShortDelay()
        return Result.success(Unit)
    }

    override suspend fun getGroupList(): Result<List<Group>> {
        randomLongDelay()
        return Result.success(
            listOf(
                Group(1, "Group 1"),
                Group(2, "Group 2"),
                Group(3, "Group 3"),
                Group(4, "Group 4"),
                Group(5, "Group 5"),
                Group(6, "Group 6"),
                Group(7, "Group 7"),
                Group(8, "Group 8"),
                Group(9, "Group 9")
            )
        )
    }

    private suspend fun randomShortDelay() {
        delay(LongRange(100, 500).random())
    }

    private suspend fun randomLongDelay() {
        delay(LongRange(500, 2000).random())
    }
}
