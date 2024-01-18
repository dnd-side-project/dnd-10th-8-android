package ac.dnd.bookkeeping.android.presentation.ui.bottombar

import ac.dnd.bookkeeping.android.presentation.common.root.ScreenRootConstant
import ac.dnd.bookkeeping.android.presentation.common.state.ApplicationState
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomBarScreen(
    appState: ApplicationState,
    bottomBarItems: List<BottomBarItem> = BottomBarItem.BOTTOM_NAV_ITEMS
) {

    AnimatedVisibility(
        visible = appState.bottomBarState.value,
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
    ) {

        BottomNavigation(
            elevation = 0.dp,
            backgroundColor = Color.White,
            modifier = Modifier.fillMaxSize()
        ) {
            val navBackStackEntry by appState.navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            bottomBarItems.forEach { screen ->

                BottomNavigationItem(
                    selected = currentRoute == screen.route,
                    icon = {
                        Icon(
                            painter = painterResource(id = screen.drawableResId),
                            contentDescription = "bottom icon"
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(id = screen.stringResId),
                            fontSize = 12.sp,
                            color = Color.Black,
                            modifier = Modifier.padding(0.dp)
                        )
                    },
                    selectedContentColor = Color.Black,
                    unselectedContentColor = Color.Gray,
                    onClick = {
                        appState.navController.navigate(screen.route) {
                            popUpTo(ScreenRootConstant.MAIN_GRAPH) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )

            }
        }

    }
}
