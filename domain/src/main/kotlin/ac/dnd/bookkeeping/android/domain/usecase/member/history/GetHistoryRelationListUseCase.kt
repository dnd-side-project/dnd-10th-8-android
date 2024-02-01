package ac.dnd.bookkeeping.android.domain.usecase.member.history

import ac.dnd.bookkeeping.android.domain.model.event.Relation
import ac.dnd.bookkeeping.android.domain.model.event.RelationGroup
import javax.inject.Inject

class GetHistoryRelationListUseCase @Inject constructor(

) {
    suspend operator fun invoke(): Result<List<Relation>> {
        // TODO fix when api update
        return Result.success(
            listOf(
                Relation(
                    id = 0,
                    name = "김진우",
                    group = RelationGroup(
                        id = 0,
                        name = "가족",
                    ),
                    giveMoney = 10000000,
                    takeMoney = 23
                ),
                Relation(
                    id = 1,
                    name = "박예리나",
                    group = RelationGroup(
                        id = 1,
                        name = "지인",
                    ),
                    giveMoney = 0,
                    takeMoney = 0
                ),
                Relation(
                    id = 2,
                    name = "이다빈",
                    group = RelationGroup(
                        id = 1,
                        name = "지인",
                    ),
                    giveMoney = 1231231223,
                    takeMoney = 12
                ),
                Relation(
                    id = 3,
                    name = "장성혁",
                    group = RelationGroup(
                        id = 2,
                        name = "직장",
                    ),
                    giveMoney = 1000,
                    takeMoney = 435345346
                ),
                Relation(
                    id = 4,
                    name = "서지원",
                    group = RelationGroup(
                        id = 3,
                        name = "가족",
                    ),
                    giveMoney = 20,
                    takeMoney = 10000000
                ),
                Relation(
                    id = 5,
                    name = "김경민",
                    group = RelationGroup(
                        id = 4,
                        name = "친구",
                    ),
                    giveMoney = 1000000,
                    takeMoney = 0
                ),
            )
        )
    }
}
