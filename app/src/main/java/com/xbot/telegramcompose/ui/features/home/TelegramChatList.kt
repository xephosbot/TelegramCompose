package com.xbot.telegramcompose.ui.features.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xbot.telegramcompose.R
import com.xbot.telegramcompose.ui.components.*
import com.xbot.telegramcompose.ui.shapes.FlowerShape
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalAnimationApi::class,
    ExperimentalLayoutApi::class
)
@Composable
fun TelegramChatListScreen(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState = rememberLazyListState()
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    CustomScaffold(
        modifier = Modifier
            .then(modifier)
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TelegramTopAppBar(
                title = "Messages",
                scrollBehavior = scrollBehavior
            ) {
                ChatFilters(
                    filters = emptyList(),
                    onSelect = { ids, selected ->  }
                )
            }
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
        TelegramChatListContent(
            accounts = emptyList(),
            state = lazyListState,
            contentPadding = innerPadding,
            modifier = Modifier.consumeWindowInsets(innerPadding),
            navigateToDetail = {}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TelegramTopAppBar(
    title: String,
    scrollBehavior: TopAppBarScrollBehavior,
    content: @Composable () -> Unit = {}
) {
    CollapsingTopAppBar(
        title = { Text(text = title) },
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
        content = content
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TelegramChatListContent(
    accounts: List<Unit>,
    state: LazyListState,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
    reverseLayout: Boolean = false,
    navigateToDetail: () -> Unit
) {
    CompositionLocalProvider(LocalOverscrollConfiguration provides null) {
        LazyColumn(
            modifier = modifier,
            state = state,
            contentPadding = contentPadding,
            reverseLayout = reverseLayout
        ) {
            /*itemsIndexed(items = accounts, key = { _, item -> item.id }) { index, account ->
                TelegramChatListItem(account) {
                    navigateToDetail()
                }
                if (index < accounts.lastIndex) {
                    Divider(
                        modifier = Modifier.padding(start = 88.dp, end = 24.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    )
                }
            }*/
        }
    }
}

@Composable
private fun TelegramChatListItem(
    //account: Account,
    onClick: () -> Unit
) {
    /*ListItem(
        modifier = Modifier
            .clickable(onClick = onClick),
        headlineContent = { Text(text = account.fullName) },
        supportingContent = { Text(text = "Sample text") },
        leadingContent = {
            ShapeableImage(
                painter = painterResource(id = account.avatar),
                contentDescription = account.fullName,
                shape = FlowerShape(),
                modifier = Modifier.size(56.dp)
            )
        },
        trailingContent = { Text("12:00") }
    )*/
}

@ExperimentalMaterial3Api
@Composable
private fun ChatFilters(
    filters: List<Unit>,
    onSelect: (List<Long>, Boolean) -> Unit
) {
    ChipGroup(
        alignment = Alignment.CenterHorizontally,
        spacing = 8.dp
    ) {
        /*if (filters.size > 1) {
            val selected = filters.filter { it.selected }.containsAll(filters)

            AnimatedFilterChip(
                selected = selected,
                onClick = {
                    onSelect(filters.map { it.id }, !selected)
                },
                label = { Text(text = "All") },
                selectedColor = MaterialTheme.colorScheme.tertiaryContainer
            )
        }

        filters.forEach { filter ->
            val selected = filters.filter { it.selected }.contains(filter)

            AnimatedFilterChip(
                selected = selected,
                onClick = {
                    onSelect(listOf(filter.id), !selected)
                },
                label = { Text(text = filter.label) }
            )
        }*/
    }
}

@Preview
@Composable
private fun TelegramChatScreenPreview() {
    val listState = rememberLazyListState()

    TelegramChatListScreen(
        lazyListState = listState
    )
}