package ac.dnd.bookkeeping.android.data.repository.feature.relation

import ac.dnd.bookkeeping.android.data.remote.local.SharedPreferencesManager
import ac.dnd.bookkeeping.android.data.remote.network.api.RelationApi
import ac.dnd.bookkeeping.android.data.remote.network.util.toDomain
import ac.dnd.bookkeeping.android.domain.model.feature.group.Group
import ac.dnd.bookkeeping.android.domain.model.feature.relation.RelationDetail
import ac.dnd.bookkeeping.android.domain.model.feature.relation.RelationSimple
import ac.dnd.bookkeeping.android.domain.repository.GroupRepository
import ac.dnd.bookkeeping.android.domain.repository.RelationRepository
import javax.inject.Inject

class RealRelationRepository @Inject constructor(
    private val relationApi: RelationApi,
    private val sharedPreferencesManager: SharedPreferencesManager
) : RelationRepository {
    override suspend fun addRelation(
        groupId: Long,
        name: String,
        imageUrl: String,
        memo: String
    ): Result<Long> {
        return relationApi.addRelation(
            groupId = groupId,
            name = name,
            imageUrl = imageUrl,
            memo = memo
        ).toDomain()
    }

    override suspend fun editRelation(
        id: Long,
        groupId: Long,
        name: String,
        imageUrl: String,
        memo: String
    ): Result<Unit> {
        return relationApi.editRelation(
            id = id,
            groupId = groupId,
            name = name,
            imageUrl = imageUrl,
            memo = memo
        )
    }

    override suspend fun deleteRelation(
        id: Long
    ): Result<Unit> {
        return relationApi.deleteRelation(
            id = id
        )
    }

    override suspend fun getRelation(
        id: Long
    ): Result<RelationDetail> {
        return relationApi.getRelation(
            id = id
        ).toDomain()
    }

    override suspend fun getRelationList(
        name: String
    ): Result<List<RelationSimple>> {
        return relationApi.getRelationList(
            name = name
        ).toDomain()
    }
}
