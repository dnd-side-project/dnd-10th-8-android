package ac.dnd.bookkeeping.android.presentation.ui.bottombar

import ac.dnd.bookkeeping.android.presentation.R
import ac.dnd.bookkeeping.android.presentation.common.root.MainRoot
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

sealed class BottomBarItem(
    val route: String,
    @DrawableRes val drawableResId: Int,
    @StringRes val stringResId: Int
) {
    data object BottomFirst :
        BottomBarItem(
            route = MainRoot.BOTTOM_FIRST,
            drawableResId = R.drawable.bottom_sample,
            stringResId = R.string.bottom_sample
        )

    data object BottomSecond :
        BottomBarItem(
            route = MainRoot.BOTTOM_SECOND,
            drawableResId = R.drawable.bottom_sample,
            stringResId = R.string.bottom_sample
        )

    data object BottomThird :
        BottomBarItem(
            route = MainRoot.BOTTOM_THIRD,
            drawableResId = R.drawable.bottom_sample,
            stringResId = R.string.bottom_sample
        )

    data object BottomFourth :
        BottomBarItem(
            route = MainRoot.BOTTOM_FOURTH,
            drawableResId = R.drawable.bottom_sample,
            stringResId = R.string.bottom_sample
        )

    data object BottomFifth :
        BottomBarItem(
            route = MainRoot.BOTTOM_FIFTH,
            drawableResId = R.drawable.bottom_sample,
            stringResId = R.string.bottom_sample
        )

    companion object {
        val BOTTOM_NAV_ITEMS = listOf<BottomBarItem>(
            BottomFirst, BottomSecond, BottomThird, BottomFourth, BottomFifth
        )
    }
}
