package ac.dnd.mour.android.presentation.model.main

import androidx.annotation.DrawableRes

data class MainBottomBarItem(
    val route: String,
    val name: String,
    @DrawableRes val iconSelectedRes: Int,
    @DrawableRes val iconUnselectedRes: Int
)
