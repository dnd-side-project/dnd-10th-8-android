package ac.dnd.bookkeeping.android.domain.usecase.history

import ac.dnd.bookkeeping.android.domain.model.event.Group
import ac.dnd.bookkeeping.android.domain.model.event.Relation
import ac.dnd.bookkeeping.android.domain.model.event.RelationGroup
import javax.inject.Inject

class GetHistoryGroupListUseCase @Inject constructor(

) {
    suspend operator fun invoke(
        historyType: String
    ): Result<List<Group>> {
        return Result.success(
            when (historyType) {
                "take" -> {
                    listOf(
                        Group(
                            1,
                            "전체-받은 마음",
                            listOf(
                                Relation(
                                    id = 4,
                                    name = "서지원",
                                    group = RelationGroup(
                                        id = 3,
                                        name = "사촌",
                                    ),
                                    giveMoney = 20,
                                    takeMoney = 1000000
                                ),
                                Relation(
                                    id = 5,
                                    name = "김경민",
                                    group = RelationGroup(
                                        id = 4,
                                        name = "친구",
                                    ),
                                    giveMoney = 100000,
                                    takeMoney = 0
                                ),
                                Relation(
                                    id = 0,
                                    name = "김진우",
                                    group = RelationGroup(
                                        id = 0,
                                        name = "가족",
                                    ),
                                    giveMoney = 100000,
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
                                    giveMoney = 12312223,
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
                                    takeMoney = 4345346
                                ),
                            )
                        ),
                        Group(
                            2,
                            "친구",
                            listOf(
                                Relation(
                                    id = 5,
                                    name = "김경민",
                                    group = RelationGroup(
                                        id = 4,
                                        name = "친구",
                                    ),
                                    giveMoney = 100000,
                                    takeMoney = 0
                                ),
                            )
                        ),
                        Group(
                            3,
                            "가족",
                            listOf(
                                Relation(
                                    id = 0,
                                    name = "김진우",
                                    group = RelationGroup(
                                        id = 0,
                                        name = "가족",
                                    ),
                                    giveMoney = 1000000,
                                    takeMoney = 23
                                ),
                            )
                        ),
                        Group(
                            4,
                            "지인",
                            listOf(
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
                                    giveMoney = 12231223,
                                    takeMoney = 12
                                ),
                            )
                        ),
                        Group(
                            5,
                            "직장",
                            listOf(
                                Relation(
                                    id = 3,
                                    name = "장성혁",
                                    group = RelationGroup(
                                        id = 2,
                                        name = "직장",
                                    ),
                                    giveMoney = 1000,
                                    takeMoney = 4345346
                                ),
                            )
                        ),
                        Group(
                            6,
                            "사촌",
                            listOf(
                                Relation(
                                    id = 4,
                                    name = "서지원",
                                    group = RelationGroup(
                                        id = 3,
                                        name = "사촌",
                                    ),
                                    giveMoney = 20,
                                    takeMoney = 1000000
                                ),
                            )
                        )
                    )
                }

                "give" -> {
                    listOf(
                        Group(
                            1,
                            "전체-준마음",
                            listOf(
                                Relation(
                                    id = 4,
                                    name = "서지원",
                                    group = RelationGroup(
                                        id = 3,
                                        name = "사촌",
                                    ),
                                    giveMoney = 20,
                                    takeMoney = 1000000
                                ),
                                Relation(
                                    id = 5,
                                    name = "김경민",
                                    group = RelationGroup(
                                        id = 4,
                                        name = "친구",
                                    ),
                                    giveMoney = 100000,
                                    takeMoney = 0
                                ),
                                Relation(
                                    id = 0,
                                    name = "김진우",
                                    group = RelationGroup(
                                        id = 0,
                                        name = "가족",
                                    ),
                                    giveMoney = 100000,
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
                                    giveMoney = 12231223,
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
                                    takeMoney = 0
                                ),
                            )
                        ),
                        Group(
                            2,
                            "친구",
                            listOf(
                                Relation(
                                    id = 5,
                                    name = "김경민",
                                    group = RelationGroup(
                                        id = 4,
                                        name = "친구",
                                    ),
                                    giveMoney = 10000,
                                    takeMoney = 0
                                ),
                            )
                        ),
                        Group(
                            3,
                            "가족",
                            listOf(
                                Relation(
                                    id = 0,
                                    name = "김진우",
                                    group = RelationGroup(
                                        id = 0,
                                        name = "가족",
                                    ),
                                    giveMoney = 100000,
                                    takeMoney = 23
                                ),
                            )
                        ),
                        Group(
                            4,
                            "지인",
                            listOf(
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
                                    giveMoney = 1231223,
                                    takeMoney = 12
                                ),
                            )
                        ),
                        Group(
                            5,
                            "직장",
                            listOf(
                                Relation(
                                    id = 3,
                                    name = "장성혁",
                                    group = RelationGroup(
                                        id = 2,
                                        name = "직장",
                                    ),
                                    giveMoney = 1000,
                                    takeMoney = 4345346
                                ),
                            )
                        ),
                        Group(
                            6,
                            "사촌",
                            listOf(
                                Relation(
                                    id = 4,
                                    name = "서지원",
                                    group = RelationGroup(
                                        id = 3,
                                        name = "사촌",
                                    ),
                                    giveMoney = 20,
                                    takeMoney = 10000000
                                ),
                            )
                        )
                    )
                }

                else -> {
                    listOf(
                        Group(
                            1,
                            "전체-전체",
                            listOf(
                                Relation(
                                    id = 4,
                                    name = "서지원",
                                    group = RelationGroup(
                                        id = 3,
                                        name = "사촌",
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
                                    giveMoney = 123121223,
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
                                    takeMoney = 43534346
                                ),
                            )
                        ),
                        Group(
                            2,
                            "친구",
                            listOf(
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
                        ),
                        Group(
                            3,
                            "가족",
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
                            )
                        ),
                        Group(
                            4,
                            "지인",
                            listOf(
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
                                    giveMoney = 1231223,
                                    takeMoney = 12
                                ),
                            )
                        ),
                        Group(
                            5,
                            "직장",
                            listOf(
                                Relation(
                                    id = 3,
                                    name = "장성혁",
                                    group = RelationGroup(
                                        id = 2,
                                        name = "직장",
                                    ),
                                    giveMoney = 1000,
                                    takeMoney = 43534546
                                ),
                            )
                        ),
                        Group(
                            6,
                            "사촌",
                            listOf(
                                Relation(
                                    id = 4,
                                    name = "서지원",
                                    group = RelationGroup(
                                        id = 3,
                                        name = "사촌",
                                    ),
                                    giveMoney = 20,
                                    takeMoney = 1000000
                                ),
                            )
                        )
                    )
                }
            }
        )
    }
}
