package ac.dnd.bookkeeping.android.presentation.ui.bottombar

import ac.dnd.bookkeeping.android.presentation.common.root.MainRootConstant
import ac.dnd.bookkeeping.android.presentation.common.root.ScreenRootConstant
import ac.dnd.bookkeeping.android.presentation.common.state.ApplicationState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.bottomGraph(
    appState: ApplicationState
) {

    navigation(
        startDestination = MainRootConstant.BOTTOM_FIRST,
        route = ScreenRootConstant.MAIN_GRAPH
    ) {

        composable(BottomBarItem.BottomFirst.route) {
            SampleScreen(appState, BottomBarItem.BottomFirst.route)
        }

        composable(BottomBarItem.BottomSecond.route) {
            SampleScreen(appState, BottomBarItem.BottomSecond.route)
        }

        composable(BottomBarItem.BottomThird.route) {
            SampleScreen(appState, BottomBarItem.BottomThird.route)
        }

        composable(BottomBarItem.BottomFourth.route) {
            SampleScreen(appState, BottomBarItem.BottomFourth.route)
        }

        composable(BottomBarItem.BottomFifth.route) {
            SampleScreen(appState, BottomBarItem.BottomFifth.route)
        }

    }

}

@Composable
fun SampleScreen(
    appState: ApplicationState,
    text: String
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 20.sp,
            color = Color.Black
        )
    }
}
