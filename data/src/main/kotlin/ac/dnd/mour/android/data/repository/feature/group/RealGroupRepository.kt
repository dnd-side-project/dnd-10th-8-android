package ac.dnd.mour.android.data.repository.feature.group

import ac.dnd.mour.android.data.remote.local.SharedPreferencesManager
import ac.dnd.mour.android.data.remote.network.api.GroupApi
import ac.dnd.mour.android.data.remote.network.util.toDomain
import ac.dnd.mour.android.domain.model.feature.group.Group
import ac.dnd.mour.android.domain.repository.GroupRepository
import javax.inject.Inject

class RealGroupRepository @Inject constructor(
    private val groupApi: GroupApi,
    private val sharedPreferencesManager: SharedPreferencesManager
) : GroupRepository {
    override suspend fun addGroup(
        name: String
    ): Result<Long> {
        return groupApi.addGroup(
            name = name
        ).toDomain()
    }

    override suspend fun editGroup(
        id: Long,
        name: String
    ): Result<Unit> {
        return groupApi.editGroup(
            id = id,
            name = name
        )
    }

    override suspend fun deleteGroup(
        id: Long
    ): Result<Unit> {
        return groupApi.deleteGroup(
            id = id
        )
    }

    override suspend fun getGroupList(): Result<List<Group>> {
        return groupApi.getGroupList().toDomain()
    }
}
