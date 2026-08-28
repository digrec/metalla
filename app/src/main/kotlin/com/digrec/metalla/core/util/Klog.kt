package com.digrec.metalla.core.util

import java.util.logging.Level
import java.util.logging.Logger

/**
 * Kotlin [Logger] wrapper.
 *
 * Created by Dejan Igrec
 */
class Klog(private val logger: Logger) {

    fun i(message: String) {
        logger.log(Level.INFO, message)
    }

    fun w(message: String) {
        logger.log(Level.WARNING, message)
    }

    fun e(message: String) {
        logger.log(Level.SEVERE, message)
    }

    fun e(t: Throwable, message: String? = null) {
        logger.log(Level.SEVERE, message, t)
    }
}

/** Returns an instance of [Klog] with given [tag] (or a caller class name as default tag). */
inline fun <reified R : Any> R.logger(tag: String = this::class.java.name): Klog {
    val logger = Logger.getLogger(tag.substringBefore(delimiter = "\$Companion"))
    return Klog(logger)
}
