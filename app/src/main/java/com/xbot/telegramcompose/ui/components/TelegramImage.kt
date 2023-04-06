package com.xbot.telegramcompose.ui.components

/*@Composable
fun TelegramImage(
    client: TelegramClient2,
    file: TdApi.File?,
    modifier: Modifier = Modifier
) {
    val photo = file?.let {
        client.downloadableFile(file).collectAsState(file.local.path, Dispatchers.IO)
    } ?: remember { mutableStateOf(null) }

    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(photo.value?.let { File(it) })
            .crossfade(true)
            .build(),
        contentDescription = null,
        modifier = modifier
    )
}*/