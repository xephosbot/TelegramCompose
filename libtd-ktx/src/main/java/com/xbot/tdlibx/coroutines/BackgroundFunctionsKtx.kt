//
// NOTE: THIS FILE IS AUTO-GENERATED by the "TdApiKtxGenerator".kt
// See: https://github.com/tdlibx/td-ktx-generator/
//
package com.xbot.tdlibx.coroutines

import com.xbot.tdlibx.core.TelegramFlow
import org.drinkless.tdlib.TdApi
import org.drinkless.tdlib.TdApi.Background
import org.drinkless.tdlib.TdApi.BackgroundType
import org.drinkless.tdlib.TdApi.Backgrounds
import org.drinkless.tdlib.TdApi.HttpUrl
import org.drinkless.tdlib.TdApi.InputBackground

/**
 * Suspend function, which constructs a persistent HTTP URL for a background.
 *
 * @param name Background name.  
 * @param type Background type.
 *
 * @return [HttpUrl] Contains an HTTP URL.
 */
suspend fun TelegramFlow.getBackgroundUrl(name: String?, type: BackgroundType?): HttpUrl =
    this.sendFunctionAsync(TdApi.GetBackgroundUrl(name, type))

/**
 * Suspend function, which returns backgrounds installed by the user.
 *
 * @param forDarkTheme Pass true to order returned backgrounds for a dark theme.
 *
 * @return [Backgrounds] Contains a list of backgrounds.
 */
suspend fun TelegramFlow.getBackgrounds(forDarkTheme: Boolean): Backgrounds =
    this.sendFunctionAsync(TdApi.GetBackgrounds(forDarkTheme))

/**
 * Suspend function, which removes background from the list of installed backgrounds.
 *
 * @param backgroundId The background identifier.
 */
suspend fun TelegramFlow.removeBackground(backgroundId: Long) =
    this.sendFunctionLaunch(TdApi.RemoveBackground(backgroundId))

/**
 * Suspend function, which resets list of installed backgrounds to its default value.
 */
suspend fun TelegramFlow.resetBackgrounds() = this.sendFunctionLaunch(TdApi.ResetBackgrounds())

/**
 * Suspend function, which searches for a background by its name.
 *
 * @param name The name of the background.
 *
 * @return [Background] Describes a chat background.
 */
suspend fun TelegramFlow.searchBackground(name: String?): Background =
    this.sendFunctionAsync(TdApi.SearchBackground(name))

/**
 * Suspend function, which changes the background selected by the user; adds background to the list
 * of installed backgrounds.
 *
 * @param background The input background to use; pass null to create a new filled background or to
 * remove the current background.  
 * @param type Background type; pass null to use the default type of the remote background or to
 * remove the current background.  
 * @param forDarkTheme Pass true if the background is changed for a dark theme.
 *
 * @return [Background] Describes a chat background.
 */
suspend fun TelegramFlow.setBackground(
  background: InputBackground?,
  type: BackgroundType?,
  forDarkTheme: Boolean
): Background = this.sendFunctionAsync(TdApi.SetBackground(background, type, forDarkTheme))