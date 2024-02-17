package ac.dnd.bookkeeping.android.presentation.ui.main.common.gallery

import ac.dnd.bookkeeping.android.domain.model.gallery.GalleryImage
import ac.dnd.bookkeeping.android.presentation.R
import ac.dnd.bookkeeping.android.presentation.common.theme.Body1
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray000
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray300
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray600
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray700
import ac.dnd.bookkeeping.android.presentation.common.theme.Gray800
import ac.dnd.bookkeeping.android.presentation.common.theme.Headline1
import ac.dnd.bookkeeping.android.presentation.common.theme.Headline3
import ac.dnd.bookkeeping.android.presentation.common.theme.Primary4
import ac.dnd.bookkeeping.android.presentation.common.theme.Space16
import ac.dnd.bookkeeping.android.presentation.common.theme.Space4
import ac.dnd.bookkeeping.android.presentation.common.util.ErrorObserver
import ac.dnd.bookkeeping.android.presentation.common.view.BottomSheetScreen
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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Divider
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
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.holix.android.bottomsheetdialog.compose.BottomSheetBehaviorProperties
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialogProperties

@Composable
fun GalleryScreen(
    appState: ApplicationState,
    onDismissRequest: () -> Unit,
    onResult: (GalleryImage) -> Unit,
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

    ErrorObserver(viewModel)

    GalleryScreen(
        appState = appState,
        model = model,
        intent = viewModel::onIntent,
        onDismissRequest = onDismissRequest,
        onResult = onResult
    )
}

@Composable
private fun GalleryScreen(
    appState: ApplicationState,
    model: GalleryModel,
    intent: (GalleryIntent) -> Unit,
    onDismissRequest: () -> Unit,
    onResult: (GalleryImage) -> Unit
) {

    val perMissionAlbumLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            intent(GalleryIntent.OnGrantPermission)
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

    BottomSheetScreen(
        onDismissRequest = onDismissRequest,
        properties = BottomSheetDialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = false,
            behaviorProperties = BottomSheetBehaviorProperties(
                state = BottomSheetBehaviorProperties.State.Expanded,
                skipCollapsed = true
            )
        )
    ) {
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
                            onDismissRequest()
                        }
                        .align(Alignment.CenterStart)
                )

                Row(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .wrapContentWidth()
                        .clickable {
                            isDropDownMenuExpanded = !isDropDownMenuExpanded
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
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
                    DropdownMenu(
                        modifier = Modifier
                            .width(186.dp)
                            .background(Gray000),
                        expanded = isDropDownMenuExpanded,
                        onDismissRequest = { isDropDownMenuExpanded = false },
                    ) {
                        model.folders.mapIndexed { index, folder ->
                            DropdownMenuItem(
                                onClick = {
                                    intent(GalleryIntent.OnChangeFolder(folder))
                                    isDropDownMenuExpanded = false
                                },
                                contentPadding = PaddingValues(0.dp)
                            ) {
                                Box {

                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 6.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                    ) {
                                        Box(
                                            modifier = Modifier.size(24.dp)
                                        ) {
                                            if (folder.name == model.currentFolder.name) {
                                                Image(
                                                    painter = painterResource(R.drawable.ic_check_line),
                                                    contentDescription = null,
                                                    colorFilter = ColorFilter.tint(Primary4)
                                                )
                                            }
                                        }
                                        Text(
                                            text = folder.name,
                                            style = Body1.merge(
                                                color = Gray700,
                                                fontWeight = FontWeight.Normal
                                            )
                                        )
                                    }
                                    if (index != model.folders.size - 1) {
                                        Divider(
                                            modifier = Modifier
                                                .align(BottomCenter)
                                                .fillMaxWidth()
                                                .height(1.dp),
                                            color = Gray300
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
                                model.galleryImages.itemSnapshotList
                                    .find { image ->
                                        image?.id == currentSelectedId
                                    }?.let { currentImage ->
                                        onDismissRequest()
                                        onResult(currentImage)
                                    }
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
}
