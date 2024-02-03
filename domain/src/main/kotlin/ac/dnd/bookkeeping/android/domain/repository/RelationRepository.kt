package ac.dnd.bookkeeping.android.domain.repository

import ac.dnd.bookkeeping.android.domain.model.relation.RelationDetail
import ac.dnd.bookkeeping.android.domain.model.relation.RelationSimple

interface RelationRepository {

    suspend fun addRelation(
        groupId: Long,
        name: String,
        imageUrl: String,
        memo: String = ""
    ): Result<Long>

    suspend fun editRelation(
        id: Long,
        groupId: Long,
        name: String,
        imageUrl: String,
        memo: String = ""
    ): Result<Unit>

    suspend fun deleteRelation(
        id: Long,
    ): Result<Unit>

    suspend fun getRelation(
        id: Long
    ): Result<RelationDetail>

    suspend fun getRelationList(
        name: String = ""
    ): Result<List<RelationSimple>>
}
