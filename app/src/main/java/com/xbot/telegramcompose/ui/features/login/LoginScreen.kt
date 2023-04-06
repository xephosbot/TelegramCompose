package com.xbot.telegramcompose.ui.features.login

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessibilityNew
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.layout.DisplayFeature
import com.xbot.telegramcompose.data.AuthState
import com.xbot.telegramcompose.ui.LocalContentType
import com.xbot.telegramcompose.ui.PaneType
import com.xbot.telegramcompose.ui.TopAppBarType
import com.xbot.telegramcompose.ui.components.AnimatedWelcomeScreen
import com.xbot.telegramcompose.ui.components.CountryPicker
import com.xbot.telegramcompose.ui.components.adaptive.AdaptivePane
import com.xbot.telegramcompose.ui.components.adaptive.AdaptiveScaffold
import com.xbot.telegramcompose.ui.theme.DefaultTheme

@Composable
fun LoginRoute(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
    displayFeatures: List<DisplayFeature>,
    onLoggedIn: () -> Unit
) {
    val loginUiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = loginUiState) {
        if (loginUiState.authState == AuthState.LoggedIn) onLoggedIn()
    }

    LoginScreen(
        modifier = modifier,
        loginUiState = loginUiState,
        displayFeatures = displayFeatures,
        onInsertNumber = viewModel::sendPhone,
        onInsertCode = viewModel::sendCode,
        onInsertPassword = viewModel::sendPassword
    )
}

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    loginUiState: LoginUiState,
    displayFeatures: List<DisplayFeature>,
    onInsertNumber: (String) -> Unit,
    onInsertCode: (String) -> Unit,
    onInsertPassword: (String) -> Unit,
) {
    var detailOpened by rememberSaveable { mutableStateOf(false) }

    AdaptivePane(
        modifier = modifier,
        detailOpened = detailOpened,
        onDetailOpen = { detailOpened = it },
        first = {
            WelcomeScreen(
                openDetail = {
                    detailOpened = true
                }
            )
        },
        second = {
            LoginScreenContent(
                loginUiState = loginUiState,
                onInsertNumber = onInsertNumber,
                onInsertCode = onInsertCode,
                onInsertPassword = onInsertPassword
            ) {
                detailOpened = false
            }
        },
        displayFeatures = displayFeatures
    )
}

@Composable
private fun LoginScreenContent(
    loginUiState: LoginUiState,
    onInsertNumber: (String) -> Unit,
    onInsertCode: (String) -> Unit,
    onInsertPassword: (String) -> Unit,
    onCloseDetail: () -> Unit
) {
    Crossfade(targetState = loginUiState.authState) { id ->
        when (id) {
            is AuthState.EnterPhone -> {
                InsertNumberScreen(
                    navigateNext = onInsertNumber,
                    navigateBack = onCloseDetail,
                    loading = loginUiState.loading
                )
            }
            is AuthState.EnterCode -> {
                InsertCodeScreen(
                    navigateNext = onInsertCode,
                    navigateBack = {
                        //TODO: Navigate back auth
                    },
                    loading = loginUiState.loading
                )
            }
            is AuthState.EnterPassword -> {
                InsertPasswordScreen(
                    navigateNext = onInsertPassword,
                    navigateBack = {
                        //TODO: Navigate back auth
                    },
                    loading = loginUiState.loading
                )
            }
            else -> {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier,
    openDetail: () -> Unit
) {
    val contentType = LocalContentType.current

    AnimatedWelcomeScreen(
        modifier = modifier,
        title = { Text(text = "Welcome to\nTelegram Compose") },
        navigationButton = {
            if (contentType.pane == PaneType.SINGLE_PANE) {
                Button(onClick = openDetail) {
                    Text(text = "Get Started")
                }
            }
        }
    ) { paddingValues ->
        val optionsList = listOf(
            Icons.Default.Language to "English (United States)",
            Icons.Default.AccessibilityNew to "Assistive options"
        )

        for ((icon, title) in optionsList) {
            Surface(
                onClick = { /*TODO*/ },
                color = Color.Transparent
            ) {
                ListItem(
                    modifier = Modifier.padding(paddingValues),
                    colors = ListItemDefaults.colors(
                        containerColor = Color.Transparent
                    ),
                    headlineText = {
                        Text(
                            text = title,
                            maxLines = 1
                        )
                    },
                    leadingContent = {
                        Icon(
                            imageVector = icon,
                            contentDescription = ""
                        )
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertNumberScreen(
    modifier: Modifier = Modifier,
    navigateNext: (String) -> Unit,
    navigateBack: () -> Unit,
    loading: Boolean = false,
) {
    val contentType = LocalContentType.current
    val navigationIcon = @Composable {
        if (contentType.pane == PaneType.SINGLE_PANE) {
            IconButton(onClick = navigateBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = ""
                )
            }
        }
    }

    var text by rememberSaveable { mutableStateOf("") }

    AdaptiveScaffold(
        modifier = modifier,
        topBar = {
            Box {
                if (contentType.topAppBar == TopAppBarType.LARGE) {
                    LargeTopAppBar(
                        title = {
                            Text(
                                text = "Your phone number",
                                maxLines = 1
                            )
                        },
                        navigationIcon = navigationIcon
                    )
                } else {
                    TopAppBar(
                        title = {
                            Text(
                                text = "Your phone number",
                                maxLines = 1
                            )
                        },
                        navigationIcon = navigationIcon
                    )
                }
                if (loading) {
                    LinearProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                    )
                }
            }
        },
        floatingActionButton = {
            Button(
                onClick = { navigateNext(text) },
                enabled = !loading
            ) {
                Text(text = "Next")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(16.dp)
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(text = "Please confirm your country code and enter your phone number.")
            CountryPicker(
                modifier = Modifier.fillMaxWidth(),
                value = text,
                onValueChange = { text = it },
                label = {
                    Text(
                        text = "Phone number",
                        maxLines = 1
                    )
                },
                placeholder = {
                    Text(
                        text = "Placeholder",
                        maxLines = 1
                    )
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertCodeScreen(
    modifier: Modifier = Modifier,
    navigateNext: (String) -> Unit,
    navigateBack: () -> Unit,
    loading: Boolean = false,
) {
    val contentType = LocalContentType.current
    val navigationIcon = @Composable {
        IconButton(onClick = navigateBack) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = ""
            )
        }
    }

    var text by rememberSaveable { mutableStateOf("") }

    AdaptiveScaffold(
        modifier = modifier,
        topBar = {
            Box {
                if (contentType.topAppBar == TopAppBarType.LARGE) {
                    LargeTopAppBar(
                        title = {
                            Text(
                                text = "Enter code",
                                maxLines = 1
                            )
                        },
                        navigationIcon = navigationIcon
                    )
                } else {
                    TopAppBar(
                        title = {
                            Text(
                                text = "Enter code",
                                maxLines = 1
                            )
                        },
                        navigationIcon = navigationIcon
                    )
                }
                if (loading) {
                    LinearProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                    )
                }
            }
        },
        floatingActionButton = {
            Button(
                onClick = { navigateNext(text) },
                enabled = !loading
            ) {
                Text(text = "Next")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(16.dp)
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(text = "We've sent the code to the Telegram app for +8(888)888-88-88 on your other device.")
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = text,
                onValueChange = { text = it }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertPasswordScreen(
    modifier: Modifier = Modifier,
    navigateNext: (String) -> Unit,
    navigateBack: () -> Unit,
    loading: Boolean = false,
) {
    val contentType = LocalContentType.current
    val navigationIcon = @Composable {
        IconButton(onClick = navigateBack) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = ""
            )
        }
    }

    var text by rememberSaveable { mutableStateOf("") }

    AdaptiveScaffold(
        modifier = modifier,
        topBar = {
            Box {
                if (contentType.topAppBar == TopAppBarType.LARGE) {
                    LargeTopAppBar(
                        title = {
                            Text(
                                text = "Enter password",
                                maxLines = 1
                            )
                        },
                        navigationIcon = navigationIcon
                    )
                } else {
                    TopAppBar(
                        title = {
                            Text(
                                text = "Enter password",
                                maxLines = 1
                            )
                        },
                        navigationIcon = navigationIcon
                    )
                }
                if (loading) {
                    LinearProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                    )
                }
            }
        },
        floatingActionButton = {
            Button(
                onClick = { navigateNext(text) },
                enabled = !loading
            ) {
                Text(text = "Next")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(16.dp)
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(text = "We've sent the code to the Telegram app for +8(888)888-88-88 on your other device.")
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = text,
                onValueChange = { text = it }
            )
        }
    }
}

@Preview
@Composable
private fun WelcomeScreenPreview() {
    DefaultTheme(dynamicColor = true) {
        Surface {
            WelcomeScreen(openDetail = {})
        }
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    DefaultTheme(dynamicColor = true) {
        Surface {
            InsertNumberScreen(
                navigateNext = {},
                navigateBack = {},
                loading = true
            )
        }
    }
}

@Preview
@Composable
private fun LoginScreenPreview1() {
    DefaultTheme(dynamicColor = true) {
        Surface {
            InsertNumberScreen(
                navigateNext = {},
                navigateBack = {},
                loading = false
            )
        }
    }
}