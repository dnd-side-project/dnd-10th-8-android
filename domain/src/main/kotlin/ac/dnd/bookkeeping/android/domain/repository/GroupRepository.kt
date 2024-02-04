package ac.dnd.bookkeeping.android.domain.repository

import ac.dnd.bookkeeping.android.domain.model.feature.group.Group

interface GroupRepository {

    suspend fun addGroup(
        name: String
    ): Result<Long>

    suspend fun editGroup(
        id: Long,
        name: String
    ): Result<Unit>

    suspend fun deleteGroup(
        id: Long,
    ): Result<Unit>

    suspend fun getGroupList(): Result<List<Group>>
}
