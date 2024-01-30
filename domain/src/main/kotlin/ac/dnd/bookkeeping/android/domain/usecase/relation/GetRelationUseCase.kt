package ac.dnd.bookkeeping.android.domain.usecase.relation

import ac.dnd.bookkeeping.android.domain.model.event.Relation
import ac.dnd.bookkeeping.android.domain.model.event.RelationGroup
import javax.inject.Inject

class GetRelationUseCase @Inject constructor(

) {
    suspend operator fun invoke(
        id: Long
    ): Result<Relation> {
        // TODO
        return Result.success(
            Relation(
                id = 6710,
                name = "Harrison House",
                group = RelationGroup(
                    id = 5104,
                    name = "Casandra Reynolds"
                ),
                giveMoney = 5892,
                takeMoney = 5706
            )
        )
    }
}
