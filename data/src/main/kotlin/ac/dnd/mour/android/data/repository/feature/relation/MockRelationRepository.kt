package ac.dnd.mour.android.data.repository.feature.relation

import ac.dnd.mour.android.domain.model.feature.relation.RelationDetailGroup
import ac.dnd.mour.android.domain.model.feature.relation.RelationDetailWithUserInfo
import ac.dnd.mour.android.domain.model.feature.relation.RelationSimple
import ac.dnd.mour.android.domain.model.feature.relation.RelationSimpleGroup
import ac.dnd.mour.android.domain.repository.RelationRepository
import javax.inject.Inject
import kotlinx.coroutines.delay

class MockRelationRepository @Inject constructor() : RelationRepository {

    override suspend fun addRelation(
        groupId: Long,
        name: String,
        imageUrl: String,
        memo: String
    ): Result<Long> {
        randomShortDelay()
        return Result.success(-1)
    }

    override suspend fun editRelation(
        id: Long,
        groupId: Long,
        name: String,
        imageUrl: String,
        memo: String
    ): Result<Unit> {
        randomShortDelay()
        return Result.success(Unit)
    }

    override suspend fun deleteRelation(
        id: Long
    ): Result<Unit> {
        randomShortDelay()
        return Result.success(Unit)
    }

    override suspend fun getRelation(
        id: Long
    ): Result<RelationDetailWithUserInfo> {
        randomShortDelay()
        return Result.success(
            RelationDetailWithUserInfo(
                id = 5902,
                name = "Mandy Dillard",
                group = RelationDetailGroup(
                    id = 6945,
                    name = "Alba Sargent"
                ),
                giveMoney = 5834,
                takeMoney = 9884,
                imageUrl = "",
                memo = ""
            )
        )
    }

    override suspend fun getRelationList(
        name: String
    ): Result<List<RelationSimple>> {
        randomLongDelay()
        return Result.success(
            listOf(
                RelationSimple(
                    id = 2290,
                    name = "Concepcion Watts",
                    group = RelationSimpleGroup(
                        id = 7722,
                        name = "Enid Patton"
                    )
                ),
                RelationSimple(
                    id = 5606,
                    name = "Lora Meadows",
                    group = RelationSimpleGroup(
                        id = 7709,
                        name = "Corinne Porter"
                    )
                ),
                RelationSimple(
                    id = 5902,
                    name = "Mandy Dillard",
                    group = RelationSimpleGroup(
                        id = 6945,
                        name = "Alba Sargent"
                    )
                )
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
