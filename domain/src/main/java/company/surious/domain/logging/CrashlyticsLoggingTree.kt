package company.surious.domain.logging

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import timber.log.Timber

object CrashlyticsLoggingTree : Timber.Tree() {

    /**
     * @param priority:
     *                  Log.VERBOSE - log to firebase
     *                  Log.DEBUG - create exception and record it to firebase
     *                  Log.INFO - set custom key tag-message
     *                  Log.ERROR - record exception to firebase
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
                FirebaseCrashlytics.getInstance()
                    .recordException(UnhandledException(message, error))
            }
        }
    }
}