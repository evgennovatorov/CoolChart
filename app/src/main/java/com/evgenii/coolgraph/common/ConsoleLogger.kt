package com.evgenii.coolgraph.common

import java.util.logging.Level
import java.util.logging.Logger.getLogger

private const val CONSOLE_LOGGER_TAG = "com.evgenii.coolgraph"

class ConsoleLogger: AppLogger {

    open val logger = getLogger(CONSOLE_LOGGER_TAG).apply {
        level = Level.INFO
    } ?: null

    override fun info(tag: String, message: String) {
        logger?.log(Level.INFO, "$tag $message")
    }

    override fun error(tag: String, message: String, th: Throwable) {
        logger?.log(Level.SEVERE, "$tag $message", th)
    }
}