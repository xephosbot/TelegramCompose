package com.xbot.telegramcompose.data

sealed class AuthState {
    object Unknown : AuthState()
    object LoggedIn : AuthState()
    object EnterPhone : AuthState()
    object EnterCode : AuthState()
    class EnterPassword(val passwordHint: String) : AuthState()
}