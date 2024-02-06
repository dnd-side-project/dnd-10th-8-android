package ac.dnd.bookkeeping.android.presentation.model.main

import androidx.annotation.DrawableRes

data class MainBottomBarItem(
    val route: String,
    val name: String,
    @DrawableRes val icon: Int
)
