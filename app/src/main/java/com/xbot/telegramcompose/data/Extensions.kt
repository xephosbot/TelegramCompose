package com.xbot.telegramcompose.data

fun Boolean.toInt() = if (this) 1 else 0

inline fun <T> Array<out T>.indexOfFirstOrLast(predicate: (T) -> Boolean): Int {
    var i = 0
    while (i <= lastIndex) {
        if (predicate(get(i))) break
        i++
    }
    return i
}