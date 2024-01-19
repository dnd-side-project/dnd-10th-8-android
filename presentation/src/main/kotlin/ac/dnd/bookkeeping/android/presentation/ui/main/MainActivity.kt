package ac.dnd.bookkeeping.android.presentation.ui.main

import ac.dnd.bookkeeping.android.presentation.common.base.BaseActivity
import ac.dnd.bookkeeping.android.presentation.common.state.ManageSystemUiState
import ac.dnd.bookkeeping.android.presentation.common.state.rememberApplicationState
import ac.dnd.bookkeeping.android.presentation.databinding.ActivityMainBinding
import ac.dnd.bookkeeping.android.presentation.ui.main.theme.BookkeepingTheme
import ac.dnd.bookkeeping.android.presentation.ui.navigation.MainNavGraph
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override val viewModel: MainViewModel by viewModels()

    override fun initView() {
        bind {
            vm = viewModel
            lifecycleOwner = this@MainActivity
        }
        setContent {
            MainUi()
        }
    }
}

@Composable
fun MainUi() {
    BookkeepingTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            val appState = rememberApplicationState()
            ManageSystemUiState(appState = appState)
            MainNavGraph(appState = appState)
        }
    }
}

@Preview
@Composable
fun PreviewMainUi() {
    MainUi()
}
