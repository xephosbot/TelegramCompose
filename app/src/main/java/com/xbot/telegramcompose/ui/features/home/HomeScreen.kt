package com.xbot.telegramcompose.ui.features.home

import android.text.format.DateUtils
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
import com.xbot.telegramcompose.ui.shapes.FlowerShape
import kotlinx.coroutines.launch
import org.drinkless.td.libcore.telegram.TdApi

@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewMode = hiltViewModel(),
) {
    val chats = viewModel.chats.collectAsStateWithLifecycle()
    val chatFilters = viewModel.chatFilters.collectAsStateWithLifecycle()

    HomeScreen(
        modifier = modifier,
        getFilePath = viewModel::getFilePath,
        chats = chats.value,
        chatFilters = chatFilters.value
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    getFilePath: suspend (TdApi.File) -> String?,
    chats: List<Chat>,
    chatFilters: List<ChatFilter>
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val lazyListState = rememberLazyListState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

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
                        onClick = { /*TODO*/ }
                    ) {
                        ShapeableImage(
                            painter = painterResource(id = R.drawable.avatar_img),
                            contentDescription = null,
                            modifier = Modifier
                                .size(30.dp),
                            shape = FlowerShape(),
                            borderWidth = 1.dp
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
                content = {
                    ChatFilters(
                        filters = chatFilters,
                        onSelect = { _, _ ->

                        }
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
                chat = chat,
                showDivider = index < chats.lastIndex,
                getFilePath = getFilePath
            ) {
                //TODO: OnClick
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun LazyItemScope.ChatListItem(
    chat: Chat,
    showDivider: Boolean,
    getFilePath: suspend (TdApi.File) -> String?,
    onClick: () -> Unit
) {
    Box(modifier = Modifier.animateItemPlacement()) {
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
                    Text(text = chat.lastMessage?.date?.let {
                        (it.toLong() * 1000).toRelativeTimeSpan()
                    } ?: "Unknown time")
                    if (chat.unreadCount > 0) {
                        Badge(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        ) {
                            Text(text = chat.unreadCount.toString())
                        }
                    }
                }
            },
            supportingText = {
                Text(
                    text = chat.lastMessage?.content?.let {
                        when (it.constructor) {
                            TdApi.MessageText.CONSTRUCTOR -> (it as TdApi.MessageText).text.text
                            TdApi.MessageVideo.CONSTRUCTOR -> "Video message"
                            TdApi.MessageCall.CONSTRUCTOR -> "Call message"
                            TdApi.MessageAudio.CONSTRUCTOR -> "Audio message"
                            TdApi.MessageSticker.CONSTRUCTOR -> "Sticker message"
                            TdApi.MessageAnimation.CONSTRUCTOR -> "Animation message"
                            TdApi.MessageLocation.CONSTRUCTOR -> "Location message"
                            TdApi.MessageVoiceNote.CONSTRUCTOR -> "Voice note message"
                            TdApi.MessageVideoNote.CONSTRUCTOR -> "Video note message"
                            TdApi.MessageContactRegistered.CONSTRUCTOR -> "Joined to Telegram!"
                            TdApi.MessageChatDeleteMember.CONSTRUCTOR -> "${(it as TdApi.MessageChatDeleteMember).userId} left the chat"
                            else -> "Unsupported message"
                        }
                    } ?: "Empty message",
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            },
            tonalElevation = if (chat.isPinned) 1.dp else 0.dp
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
    onSelect: (List<Long>, Boolean) -> Unit
) {
    ChipGroup(
        alignment = Alignment.CenterHorizontally,
        spacing = 8.dp
    ) {
        if (filters.size > 1) {
            //val selected = filters.filter { it.selected }.containsAll(filters)

            AnimatedFilterChip(
                //selected = selected,
                selected = true,
                onClick = {
                    //onSelect(filters.map { it.id }, !selected)
                },
                label = { Text(text = "All") },
                selectedColor = MaterialTheme.colorScheme.tertiaryContainer
            )
        }

        filters.forEach { filter ->
            //val selected = filters.filter { it.selected }.contains(filter)

            AnimatedFilterChip(
                //selected = selected,
                selected = true,
                onClick = {
                    //onSelect(listOf(filter.id), !selected)
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

private fun Int.toTime(): String {
    val duration = this.toLong()
    val hours: Long = (duration / (60 * 60))
    val minutes = (duration % (60 * 60) / (60))
    val seconds = (duration % (60 * 60) % (60))
    return when {
        minutes == 0L && hours == 0L -> String.format("0:%02d", seconds)
        hours == 0L -> String.format("%02d:%02d", minutes, seconds)
        else -> String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }
}
