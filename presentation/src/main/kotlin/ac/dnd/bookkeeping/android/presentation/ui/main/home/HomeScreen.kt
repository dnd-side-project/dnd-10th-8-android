package ac.dnd.bookkeeping.android.presentation.ui.main.home

import ac.dnd.bookkeeping.android.presentation.common.view.CustomSnackBarHost
import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import ac.dnd.bookkeeping.android.presentation.ui.main.home.bookkeeping.BookkeepingScreen
import ac.dnd.bookkeeping.android.presentation.ui.main.home.setting.SettingScreen
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarResult
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    appState: ApplicationState,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    var selectedItem by rememberSaveable { mutableIntStateOf(0) }
    var snackBarIsShowingState by remember { mutableStateOf(false) }
    val pagerState = rememberPagerState(
        pageCount = { 2 }
    )

    LaunchedEffect(selectedItem) {
        pagerState.animateScrollToPage(selectedItem)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        snackbarHost = CustomSnackBarHost,
        bottomBar = {
            HomeBottomBarScreen(
                selectedItem = selectedItem,
                onClick = {
                    selectedItem = it
                }
            )
        }
    ) { innerPadding ->

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.padding(innerPadding),
            userScrollEnabled = false
        ) { page ->
            when (page) {
                0 -> {
                    BookkeepingScreen(
                        appState = appState
                    )
                }

                1 -> {
                    SettingScreen(
                        appState = appState,
                        onShowSnackBar = {
                            snackBarIsShowingState = true
                        }
                    )
                }
            }
        }
    }

    if (snackBarIsShowingState) {
        LaunchedEffect(Unit) {
            scaffoldState.snackbarHostState.showSnackbar("show snackBar")
                .let {
                    if (it == SnackbarResult.Dismissed) {
                        snackBarIsShowingState = false
                    }
                }
        }
    }
}
