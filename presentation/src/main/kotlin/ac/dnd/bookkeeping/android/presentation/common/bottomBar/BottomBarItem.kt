package ac.dnd.bookkeeping.android.presentation.common.bottomBar

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

sealed class BottomBarItem(
    val route: String,
    @DrawableRes val drawableResId: Int,
    @StringRes val stringResId: Int
) {


    companion object {
        val BOTTOM_NAV_ITEMS = listOf<BottomBarItem>()
    }
}