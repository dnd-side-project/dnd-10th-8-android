package ac.dnd.mour.android.domain.repository

import ac.dnd.mour.android.domain.model.feature.relation.RelationDetail
import ac.dnd.mour.android.domain.model.feature.relation.RelationDetailWithUserInfo
import ac.dnd.mour.android.domain.model.feature.relation.RelationSimple

interface RelationRepository {

    suspend fun addRelation(
        groupId: Long,
        name: String,
        imageUrl: String,
        memo: String
    ): Result<Long>

    suspend fun editRelation(
        id: Long,
        groupId: Long,
        name: String,
        imageUrl: String,
        memo: String
    ): Result<Unit>

    suspend fun deleteRelation(
        id: Long,
    ): Result<Unit>

    suspend fun getRelation(
        id: Long
    ): Result<RelationDetailWithUserInfo>

    suspend fun getRelationList(
        name: String
    ): Result<List<RelationSimple>>
}
