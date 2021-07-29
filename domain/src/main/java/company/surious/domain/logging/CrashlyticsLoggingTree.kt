package company.surious.domain.logging

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import timber.log.Timber

object CrashlyticsLoggingTree : Timber.Tree() {

    private val ERROR_DESCRIPTION = "Error description"

    /**
     * @param priority:
     *                  Log.VERBOSE - log to firebase
     *                  Log.DEBUG - create exception and record it to firebase
     *                  Log.INFO - set custom key tag-message
     *                  Log.ERROR - set custom key ERROR_DESCRITPION-message
     * @param tag
     * @param message
     * @param error
     */
    override fun log(priority: Int, tag: String?, message: String, error: Throwable?) {
        when (priority) {
            Log.VERBOSE -> FirebaseCrashlytics.getInstance().log("$tag: $message")
            Log.DEBUG -> FirebaseCrashlytics.getInstance()
                .recordException(DebugLogException("$tag: $message"))
            Log.INFO -> FirebaseCrashlytics.getInstance().setCustomKey(tag!!, message)
            Log.ERROR -> {
                val checkedError = error ?: DebugLogException("$tag: $message")
                FirebaseCrashlytics.getInstance().recordException(checkedError)
            }
        }
    }
}