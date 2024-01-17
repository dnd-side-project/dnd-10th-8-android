package ac.dnd.bookkeeping.android.presentation.common.bottomBar

import ac.dnd.bookkeeping.android.presentation.common.root.MainRoot
import ac.dnd.bookkeeping.android.presentation.common.root.ScreenRoot
import ac.dnd.bookkeeping.android.presentation.common.state.ApplicationState
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.bottomGraph(
    appState: ApplicationState
) {

    navigation(
        startDestination = MainRoot.BOTTOM_FIRST,
        route = ScreenRoot.MAIN_GRAPH
    ) {

        composable(BottomBarItem.BottomFirst.route) {
            FirstScreen(appState)
        }

        composable(BottomBarItem.BottomSecond.route) {
            SecondScreen(appState)
        }

        composable(BottomBarItem.BottomThird.route) {
            ThirdScreen(appState)
        }

        composable(BottomBarItem.BottomFourth.route) {
            FourthScreen(appState)
        }

        composable(BottomBarItem.BottomFifth.route) {
            FifthScreen(appState)
        }

    }

}

@Composable
fun FirstScreen(appState: ApplicationState) {
}

@Composable
fun SecondScreen(appState: ApplicationState) {
}

@Composable
fun ThirdScreen(appState: ApplicationState) {
}

@Composable
fun FourthScreen(appState: ApplicationState) {
}

@Composable
fun FifthScreen(appState: ApplicationState) {
}

