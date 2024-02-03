package ac.dnd.bookkeeping.android.domain.usecase.relation

import ac.dnd.bookkeeping.android.domain.model.legacy.RelationLegacy
import ac.dnd.bookkeeping.android.domain.model.legacy.RelationGroupLegacy
import javax.inject.Inject

class GetRelationUseCase @Inject constructor(

) {
    suspend operator fun invoke(
        id: Long
    ): Result<RelationLegacy> {
        // TODO
        return Result.success(
            RelationLegacy(
                id = 6710,
                name = "Harrison House",
                group = RelationGroupLegacy(
                    id = 5104,
                    name = "Casandra Reynolds"
                ),
                giveMoney = 5892,
                takeMoney = 5706
            )
        )
    }
}
