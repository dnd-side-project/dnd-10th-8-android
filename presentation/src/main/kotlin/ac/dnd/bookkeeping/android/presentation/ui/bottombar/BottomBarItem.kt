package ac.dnd.bookkeeping.android.presentation.ui.bottombar

import ac.dnd.bookkeeping.android.presentation.R
import ac.dnd.bookkeeping.android.presentation.common.root.MainRootConstant
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

sealed class BottomBarItem(
    val route: String,
    @DrawableRes val drawableResId: Int,
    @StringRes val stringResId: Int
) {
    data object BottomFirst :
        BottomBarItem(
            route = MainRootConstant.BOTTOM_FIRST,
            drawableResId = R.drawable.bottom_sample,
            stringResId = R.string.bottom_sample
        )

    data object BottomSecond :
        BottomBarItem(
            route = MainRootConstant.BOTTOM_SECOND,
            drawableResId = R.drawable.bottom_sample,
            stringResId = R.string.bottom_sample
        )

    data object BottomThird :
        BottomBarItem(
            route = MainRootConstant.BOTTOM_THIRD,
            drawableResId = R.drawable.bottom_sample,
            stringResId = R.string.bottom_sample
        )

    data object BottomFourth :
        BottomBarItem(
            route = MainRootConstant.BOTTOM_FOURTH,
            drawableResId = R.drawable.bottom_sample,
            stringResId = R.string.bottom_sample
        )

    data object BottomFifth :
        BottomBarItem(
            route = MainRootConstant.BOTTOM_FIFTH,
            drawableResId = R.drawable.bottom_sample,
            stringResId = R.string.bottom_sample
        )

    companion object {
        val BOTTOM_NAV_ITEMS = listOf<BottomBarItem>(
            BottomFirst, BottomSecond, BottomThird, BottomFourth, BottomFifth
        )
    }
}
