package ac.dnd.bookkeeping.android.presentation.model.history

import ac.dnd.bookkeeping.android.presentation.R

enum class HistoryDetailGrowthType(
    val typeName: String,
    val price: Long,
    val imageResource: Int,
    val level: Int
) {
    LEVEL_ONE(
        typeName = "어색한 사이",
        price = 0,
        imageResource = R.drawable.ic_circle_plus,
        level = 1
    ),
    LEVEL_TWO(
        typeName = "알아가는 사이",
        price = 30_000,
        imageResource = R.drawable.ic_circle_plus,
        level = 2
    ),
    LEVEL_THREE(
        typeName = "꽤 가까운 사이",
        price = 60_000,
        imageResource = R.drawable.ic_circle_plus,
        level = 3
    ),
    LEVEL_FOUR(
        typeName = "친한 사이",
        price = 100_000,
        imageResource = R.drawable.ic_circle_plus,
        level = 4
    ),
    LEVEL_FIVE(
        typeName = "믿음직한 사이",
        price = 500_000,
        imageResource = R.drawable.ic_circle_plus,
        level = 5
    ),
    LEVEL_SIX(
        typeName = "평생을 함께 할 사이",
        price = 1_000_000,
        imageResource = R.drawable.ic_circle_plus,
        level = 6
    );

    companion object {
        fun getGrowthType(
            totalMoney: Long
        ): HistoryDetailGrowthType {
            return when (totalMoney) {
                in LEVEL_ONE.price until LEVEL_TWO.price -> LEVEL_ONE
                in LEVEL_TWO.price until LEVEL_THREE.price -> LEVEL_TWO
                in LEVEL_THREE.price until LEVEL_FOUR.price -> LEVEL_THREE
                in LEVEL_FOUR.price until LEVEL_FIVE.price -> LEVEL_FOUR
                in LEVEL_FIVE.price until LEVEL_SIX.price -> LEVEL_FIVE
                else -> LEVEL_SIX
            }
        }

        fun getTypeString(
            type: HistoryDetailGrowthType
        ): String {
            return when (type) {
                LEVEL_ONE -> "(~${LEVEL_TWO.price / 10000}만원 미만)"
                LEVEL_TWO -> "(${LEVEL_TWO.price / 10000}만원 이상 ~ ${LEVEL_THREE.price / 10000}만원 미만)"
                LEVEL_THREE -> "(${LEVEL_THREE.price / 10000}만원 이상 ~ ${LEVEL_FOUR.price / 10000}만원 미만)"
                LEVEL_FOUR -> "(${LEVEL_FOUR.price / 10000}만원 이상 ~ ${LEVEL_FIVE.price / 10000}만원 미만)"
                LEVEL_FIVE -> "(${LEVEL_FIVE.price / 10000}만원 이상 ~ ${LEVEL_SIX.price / 10000}만원 미만)"
                LEVEL_SIX -> "(${LEVEL_SIX.price / 10000}만원 이상 ~)"
            }
        }
    }
}
