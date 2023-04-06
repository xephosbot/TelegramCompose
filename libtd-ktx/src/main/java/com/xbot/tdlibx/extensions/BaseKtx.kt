package com.xbot.tdlibx.extensions

import com.xbot.tdlibx.core.TelegramFlow

interface BaseKtx {
    /**
     * Instance of the [TelegramFlow] connecting entities to the Telegram Client
     */
    val api: TelegramFlow
}