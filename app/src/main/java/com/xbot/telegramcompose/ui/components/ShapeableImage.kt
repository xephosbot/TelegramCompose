package com.xbot.telegramcompose.ui.components

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toDrawable
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.xbot.telegramcompose.model.ProfilePhoto
import com.xbot.telegramcompose.ui.utils.rememberInitialsDrawable
import org.drinkless.tdlib.TdApi
import java.io.File

@Composable
fun AvatarImage(
    photo: ProfilePhoto?,
    contentDescription: String,
    getFilePath: suspend (TdApi.File) -> String?,
    modifier: Modifier = Modifier,
    shape: Shape = CircleShape,
    borderWidth: Dp = 0.dp,
    borderColor: Color = MaterialTheme.colorScheme.outlineVariant
) {
    val context = LocalContext.current
    var filePath by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(photo) {
        photo?.let {
            filePath = getFilePath(it.file)
        }
    }

    val file = filePath?.let { File(it) }
    val loadingPlaceholder = photo?.let { bitmapFromByteArray(it.thumbnail) }
    val initialsPlaceholder = rememberInitialsDrawable(contentDescription)

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context)
            .placeholder(loadingPlaceholder?.toDrawable(context.resources))
            .error(initialsPlaceholder)
            .data(file)
            .size(Size.ORIGINAL)
            .crossfade(true)
            .build()
    )

    ShapeableImage(
        painter = painter,
        contentDescription = contentDescription,
        modifier = modifier,
        shape = shape,
        borderWidth = borderWidth,
        borderColor = borderColor
    )
}

@Composable
fun ShapeableImage(
    bitmap: ImageBitmap,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    shape: Shape = CircleShape,
    borderWidth: Dp = 0.dp,
    borderColor: Color = MaterialTheme.colorScheme.outlineVariant
) {
    Image(
        bitmap = bitmap,
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .then(modifier)
            .graphicsLayer {
                this.clip = true
                this.shape = shape
            }
            .conditional(borderWidth > 0.dp) {
                border(
                    border = BorderStroke(borderWidth, borderColor),
                    shape = shape
                ).padding(borderWidth)
            }
    )
}

@Composable
fun ShapeableImage(
    painter: Painter,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    shape: Shape = CircleShape,
    borderWidth: Dp = 0.dp,
    borderColor: Color = MaterialTheme.colorScheme.outlineVariant
) {
    Image(
        painter = painter,
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .then(modifier)
            .graphicsLayer {
                this.clip = true
                this.shape = shape
            }
            .conditional(borderWidth > 0.dp) {
                border(
                    border = BorderStroke(borderWidth, borderColor),
                    shape = shape
                ).padding(borderWidth)
            }
    )
}

@Composable
fun bitmapFromByteArray(bitmapData: ByteArray): Bitmap {
    return remember(bitmapData) {
        BitmapFactory.decodeByteArray(bitmapData, 0, bitmapData.size)
    }
}

fun Modifier.conditional(
    condition : Boolean,
    modifier : Modifier.() -> Modifier
) : Modifier {
    return if (condition) {
        then(modifier(Modifier))
    } else {
        this
    }
}