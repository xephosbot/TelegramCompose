package com.xbot.telegramcompose.ui.navigation

interface TelegramDestination {
    val route: String

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach{ arg ->
                append("/$arg")
            }
        }
    }

    fun withArgsFormat(vararg args: String) : String {
        return buildString {
            append(route)
            args.forEach{ arg ->
                append("/{$arg}")
            }
        }
    }
}

interface TelegramNavigationStack {
    fun next(): TelegramDestination?
    fun previous(): TelegramDestination?
}

object Login: TelegramDestination {
    override val route: String = "login"

    object InsertNumber: TelegramDestination, TelegramNavigationStack {
        override val route: String = "login/insert_number"

        override fun next(): TelegramDestination = InsertCode
        override fun previous(): TelegramDestination? = null
    }
    object InsertCode: TelegramDestination, TelegramNavigationStack {
        override val route: String = "login/insert_code"

        override fun next(): TelegramDestination = InsertPassword
        override fun previous(): TelegramDestination = InsertNumber
    }
    object InsertPassword: TelegramDestination, TelegramNavigationStack {
        override val route: String = "login/insert_password"

        override fun next(): TelegramDestination? = null
        override fun previous(): TelegramDestination = InsertCode
    }
}

object Home: TelegramDestination {
    override val route: String = "home"

    object ChatList: TelegramDestination {
        override val route: String = "home/chat_list"
    }
    object CreateChat: TelegramDestination {
        override val route: String = "home/create_chat"
    }
    object Settings: TelegramDestination {
        override val route: String = "home/settings"
    }
}