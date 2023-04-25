package com.xbot.telegramcompose.ui.features.home

import android.net.Uri
import android.text.format.DateUtils
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Badge
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.xbot.telegramcompose.R
import com.xbot.telegramcompose.model.Chat
import com.xbot.telegramcompose.model.ChatFilter
import com.xbot.telegramcompose.ui.components.AnimatedFilterChip
import com.xbot.telegramcompose.ui.components.AnimatedFloatingActionButton
import com.xbot.telegramcompose.ui.components.AvatarImage
import com.xbot.telegramcompose.ui.components.ChipGroup
import com.xbot.telegramcompose.ui.components.CollapsingTopAppBar
import com.xbot.telegramcompose.ui.components.CustomScaffold
import com.xbot.telegramcompose.ui.components.ShapeableImage
import com.xbot.telegramcompose.ui.components.bitmapFromByteArray
import com.xbot.telegramcompose.ui.shapes.FlowerShape
import com.xbot.telegramcompose.ui.theme.elevation
import kotlinx.coroutines.launch
import org.drinkless.tdlib.TdApi

@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewMode = hiltViewModel(),
) {
    val chats by viewModel.chats.collectAsStateWithLifecycle()
    val chatFilters by viewModel.chatFilters.collectAsStateWithLifecycle()
    val selectedChatFilter by viewModel.selectedChatFilter.collectAsStateWithLifecycle()

    HomeScreen(
        modifier = modifier,
        getFilePath = viewModel::getFilePath,
        setSelectedFilter = viewModel::setSelectedFilter,
        chats = chats,
        selectedChatFilter = selectedChatFilter,
        chatFilters = chatFilters
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    getFilePath: suspend (TdApi.File) -> String?,
    setSelectedFilter: (Int) -> Unit,
    chats: List<Chat>,
    selectedChatFilter: Int,
    chatFilters: List<ChatFilter>
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val lazyListState = rememberLazyListState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> selectedImageUri = uri }
    )

    CustomScaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CollapsingTopAppBar(
                title = { Text(text = "Messages") },
                actions = {
                    IconButton(
                        onClick = { /*TODO*/ }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = ""
                        )
                    }
                    IconButton(
                        onClick = {
                            singlePhotoPickerLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        }
                    ) {
                        val painter = rememberAsyncImagePainter(
                            model = ImageRequest.Builder(LocalContext.current)
                                .error(R.drawable.avatar_img)
                                .data(selectedImageUri)
                                .build()
                        )

                        ShapeableImage(
                            painter = painter,
                            contentDescription = null,
                            modifier = Modifier.size(30.dp),
                            shape = FlowerShape(),
                            borderWidth = 1.dp
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
                content = {
                    ChatFilters(
                        filters = chatFilters,
                        selectedChatFilter = selectedChatFilter,
                        onSelectFilter = setSelectedFilter
                    )
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            AnimatedFloatingActionButton(
                onClick = {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = "Floating action button",
                            actionLabel = "Ok"
                        )
                    }
                },
                listState = lazyListState
            ) {
                Icon(
                    imageVector = Icons.Default.Create,
                    contentDescription = ""
                )
            }
        }
    ) { innerPadding ->
        HomeScreenChatList(
            getFilePath = getFilePath,
            chats = chats,
            listState = lazyListState,
            contentPadding = innerPadding
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HomeScreenChatList(
    modifier: Modifier = Modifier,
    getFilePath: suspend (TdApi.File) -> String?,
    chats: List<Chat>,
    listState: LazyListState,
    contentPadding: PaddingValues
) {
    LazyColumn(
        modifier = modifier,
        state = listState,
        contentPadding = contentPadding
    ) {
        itemsIndexed(
            items = chats,
            key = { _, chat -> chat.id }
        ) { index, chat ->
            ChatListItem(
                modifier = Modifier.animateItemPlacement(),
                chat = chat,
                showDivider = index < chats.lastIndex,
                getFilePath = getFilePath
            ) {
                //TODO: OnClick
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChatListItem(
    modifier: Modifier = Modifier,
    chat: Chat,
    showDivider: Boolean,
    getFilePath: suspend (TdApi.File) -> String?,
    onClick: () -> Unit
) {
    Box(modifier = modifier) {
        ListItem(
            modifier = Modifier.clickable { onClick() },
            leadingContent = {
                AvatarImage(
                    photo = chat.photo,
                    contentDescription = chat.title,
                    getFilePath = getFilePath,
                    modifier = Modifier.size(56.dp)
                )
            },
            headlineText = {
                Text(
                    text = chat.title,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            },
            trailingContent = {
                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = chat.lastMessage?.date?.let {
                            (it * 1000).toRelativeTimeSpan()
                        } ?: "Unknown time"
                    )
                    if (chat.unreadCount > 0) {
                        Badge(
                            containerColor = if (chat.isMuted) {
                                MaterialTheme.colorScheme.secondaryContainer
                            } else {
                                MaterialTheme.colorScheme.primaryContainer
                            }
                        ) {
                            Text(text = chat.unreadCount.toString())
                        }
                    }
                }
            },
            supportingText = {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    chat.lastMessage?.content?.forEach { content ->
                        content.thumbnail?.let {
                            ShapeableImage(
                                modifier = Modifier.size(16.dp),
                                bitmap = bitmapFromByteArray(it).asImageBitmap(),
                                contentDescription = "",
                                shape = RoundedCornerShape(4.dp)
                            )
                        }
                    }

                    Text(
                        text = chat.lastMessage?.content?.first()?.text?.ifEmpty { "Deleted account" } ?: "Empty message",
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }
            },
            tonalElevation = if (chat.isPinned) {
                MaterialTheme.elevation.level1
            } else {
                MaterialTheme.elevation.level0
            }
        )
        if (showDivider) {
            Divider(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(
                        start = 88.dp,
                        end = 24.dp
                    ),
                color = MaterialTheme.colorScheme.surfaceVariant
            )
        }
    }
}

@ExperimentalMaterial3Api
@Composable
private fun ChatFilters(
    filters: List<ChatFilter>,
    selectedChatFilter: Int,
    onSelectFilter: (Int) -> Unit,
) {
    ChipGroup(
        alignment = Alignment.CenterHorizontally,
        spacing = 8.dp
    ) {
        if (filters.isNotEmpty()) {
            AnimatedFilterChip(
                selected = selectedChatFilter == 0,
                onClick = {
                    onSelectFilter(0)
                },
                label = { Text(text = "All") },
                selectedColor = MaterialTheme.colorScheme.tertiaryContainer
            )
        }

        filters.forEach { filter ->
            AnimatedFilterChip(
                selected = filter.id == selectedChatFilter,
                onClick = {
                    onSelectFilter(filter.id)
                },
                label = { Text(text = filter.title) }
            )
        }
    }
}

private fun Long.toRelativeTimeSpan(): String =
    DateUtils.getRelativeTimeSpanString(
        this,
        System.currentTimeMillis(),
        DateUtils.SECOND_IN_MILLIS
    ).toString()
