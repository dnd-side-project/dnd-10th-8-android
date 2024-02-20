package ac.dnd.mour.android.presentation.ui.invalid

import ac.dnd.mour.android.presentation.R
import ac.dnd.mour.android.presentation.common.theme.MourTheme
import ac.dnd.mour.android.presentation.common.view.DialogScreen
import ac.dnd.mour.android.presentation.ui.main.MainActivity
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InvalidJwtTokenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }

    @Composable
    fun MainScreen(
        viewModel: InvalidJwtTokenViewModel = hiltViewModel()
    ) {
        val context = LocalContext.current
        MourTheme {
            DialogScreen(
                title = stringResource(R.string.invalid_jwt_token_dialog_title),
                message = stringResource(R.string.invalid_jwt_token_dialog_content),
                onConfirm = {
                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)
                    finishAfterTransition()
                },
                onDismissRequest = {}
            )
        }
    }
}
