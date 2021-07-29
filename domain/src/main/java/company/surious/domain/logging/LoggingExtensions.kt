package company.surious.domain.logging

import timber.log.Timber

fun logV(tag: String, message: String) {
    Timber.tag(tag).v(message)
}

fun logD(tag: String, message: String) {
    Timber.tag(tag).d(message)
}

fun logI(tag: String, message: String) {
    Timber.tag(tag).i(message)
}

fun logE(tag: String, error: Throwable, message: String? = null) {
    if (message != null) {
        Timber.tag(tag).e(error, message)
    } else {
        Timber.tag(tag).e(error)
    }
}

fun logUnhandledError(error: Throwable, message: String? = null) {
    logE(LogTags.UNHANDLED, error, message)
}

fun logE(error: Throwable, message: String) {
    Timber.e(error, message)
}

fun logDebug(message: String) {
    logD(LogTags.DEBUG, message)
}

fun logNav(message: String) {
    logV(LogTags.NAVIGATION, message)
}

fun logFlow(message: String) {
    logV(LogTags.FLOW, message)
}

fun logValue(tag: String, message: String) {
    logI(tag, message)
}