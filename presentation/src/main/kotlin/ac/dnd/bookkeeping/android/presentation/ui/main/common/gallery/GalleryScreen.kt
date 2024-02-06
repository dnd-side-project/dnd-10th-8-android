package ac.dnd.bookkeeping.android.presentation.ui.main.common.gallery

import ac.dnd.bookkeeping.android.presentation.R
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray000
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray300
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray600
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray800
import ac.dnd.bookkeeping.android.presentation.common.theme.Headline1
import ac.dnd.bookkeeping.android.presentation.common.theme.Headline3
import ac.dnd.bookkeeping.android.presentation.common.theme.Primary4
import ac.dnd.bookkeeping.android.presentation.common.theme.Space16
import ac.dnd.bookkeeping.android.presentation.common.theme.Space4
import ac.dnd.bookkeeping.android.presentation.ui.main.ApplicationState
import ac.dnd.bookkeeping.android.presentation.ui.main.common.gallery.item.GalleryItemContent
import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems

@Composable
fun GalleryScreen(
    appState: ApplicationState,
    viewModel: GalleryViewModel = hiltViewModel()
) {
    val model: GalleryModel = Unit.let {
        val state by viewModel.state.collectAsStateWithLifecycle()
        val folders by viewModel.folders.collectAsStateWithLifecycle()
        val currentFolder by viewModel.currentFolder.collectAsStateWithLifecycle()
        val images = viewModel.galleryPhotoList.collectAsLazyPagingItems()
        GalleryModel(
            state = state,
            folders = folders,
            currentFolder = currentFolder,
            galleryImages = images
        )
    }
    val perMissionAlbumLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            viewModel.onIntent(GalleryIntent.OnGrantPermission)
        }
    }
    var currentSelectedId by remember { mutableLongStateOf(-1) }
    var isDropDownMenuExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            perMissionAlbumLauncher.launch(
                Manifest.permission.READ_MEDIA_IMAGES
            )
        } else {
            perMissionAlbumLauncher.launch(
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }
    }

    Column(
        modifier = Modifier
            .background(Gray000)
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(
                    horizontal = 20.dp,
                    vertical = 13.dp
                )
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_close),
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        appState.navController.popBackStack()
                    }
                    .align(Alignment.CenterStart)
            )

            Row(
                modifier = Modifier
                    .align(Alignment.Center)
                    .clickable {
                        isDropDownMenuExpanded = !isDropDownMenuExpanded
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = model.currentFolder.name,
                    style = Headline1.merge(
                        color = Gray800,
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Spacer(modifier = Modifier.width(Space4))
                Image(
                    painter = painterResource(id = R.drawable.ic_chevron_down),
                    contentDescription = null,
                    modifier = Modifier
                        .rotate(if (isDropDownMenuExpanded) 180f else 0f)
                        .size(Space16)
                )
                if (isDropDownMenuExpanded) {
                    DropdownMenu(
                        modifier = Modifier
                            .wrapContentSize()
                            .background(Gray300),
                        expanded = isDropDownMenuExpanded,
                        onDismissRequest = { isDropDownMenuExpanded = false },
                    ) {
                        model.folders.map { folder ->
                            DropdownMenuItem(
                                onClick = {
                                    viewModel.onIntent(GalleryIntent.OnChangeFolder(folder))
                                    isDropDownMenuExpanded = false
                                }
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = folder.name,
                                        style = Headline3
                                    )
                                }
                            }
                        }
                    }
                }

            }

            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.CenterEnd)
                    .clickable {
                        if (currentSelectedId > 0) {
                            // TODO next
                        }
                    }
            ) {
                Text(
                    text = "확인",
                    style = Headline3.merge(
                        color = if (currentSelectedId > 0) Primary4 else Gray600,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }

        }

        Box(Modifier.fillMaxSize()) {
            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                columns = GridCells.Fixed(3),
                verticalArrangement = Arrangement.spacedBy(1.dp),
                horizontalArrangement = Arrangement.spacedBy(1.dp)
            ) {
                items(model.galleryImages.itemCount) { index ->
                    model.galleryImages[index]?.let { gallery ->
                        GalleryItemContent(
                            galleryImage = gallery,
                            currentSelectedImageId = currentSelectedId,
                            onSelectImage = {
                                currentSelectedId = gallery.id
                            },
                            onDeleteImage = {
                                currentSelectedId = -1
                            }
                        )
                    }
                }
            }
        }
    }
}
