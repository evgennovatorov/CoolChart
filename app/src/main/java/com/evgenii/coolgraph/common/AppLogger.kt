package com.evgenii.coolgraph.common

interface AppLogger {

    fun info(tag: String, message: String)
    fun error(tag: String, message: String, th: Throwable)
}