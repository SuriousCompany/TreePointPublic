package company.surious.domain.logging

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import timber.log.Timber

/**
 * log verbose, log crashlytics
 */
fun logVerbose(tag: String, message: String) {
    Timber.tag(tag).v(message)
}

/**
 * log info, record crashlytics custom key
 */
fun logValue(tag: String, message: String) {
    Timber.tag(tag).i(message)
}

/**
 * log error, record UnhandledException with message to crashlytics issue
 */
fun logUnhandledError(error: Throwable, message: String? = null) {
    logE(LogTags.UNHANDLED, error, message)
}

/**
 * log error, record UnhandledException with message to crashlytics issue
 */
fun logE(tag: String, error: Throwable, message: String? = null) {
    if (message != null) {
        Timber.tag(tag).e(error, message)
    } else {
        Timber.tag(tag).e(error)
    }
}

/**
 * log debug, create crashlytics issue
 */
fun logDebug(message: String) {
    Timber.tag(LogTags.DEBUG).d(message)
}

/**
 * log verbose, log crashlytics
 */
fun logFlow(message: String) {
    logVerbose(LogTags.FLOW, message)
}

/**
 * log verbose, log crashlytics
 */
fun Fragment.logNavigation() {
    logVerbose(LogTags.NAVIGATION, javaClass.simpleName)
}

/**
 * log verbose, log crashlytics
 */
fun FragmentActivity.logNavigation() {
    logVerbose(LogTags.NAVIGATION, javaClass.simpleName)
}