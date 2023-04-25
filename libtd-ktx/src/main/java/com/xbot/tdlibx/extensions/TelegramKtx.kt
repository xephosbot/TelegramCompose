//
// NOTE: THIS FILE IS AUTO-GENERATED by the "ExtensionsGenerator".kt
// See: https://github.com/tdlibx/td-ktx-generator/
//
package com.xbot.tdlibx.extensions

import com.xbot.tdlibx.core.TelegramFlow

/**
 * Interface for access all Telegram objects extension functions. Contains 319 extensions
 */
interface TelegramKtx : BasicGroupKtx, CallKtx, ChatKtx, FileKtx, GroupCallKtx, MessageKtx,
    NotificationGroupKtx, NotificationSoundKtx, ProxyKtx, SecretChatKtx, SessionKtx, SupergroupKtx,
    UserKtx, CommonKtx {
  /**
   * Instance of the [TelegramFlow] connecting extensions to the Telegram Client
   */
  override val api: TelegramFlow
}