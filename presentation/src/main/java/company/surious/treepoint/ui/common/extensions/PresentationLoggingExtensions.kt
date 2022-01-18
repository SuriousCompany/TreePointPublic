package company.surious.treepoint.ui.common.extensions

import androidx.appcompat.app.AppCompatActivity
import company.surious.domain.logging.LogTags
import company.surious.domain.logging.logVerbose

/**
 * log verbose, log crashlytics
 */
fun AppCompatActivity.logNavigation() {
    logVerbose(LogTags.NAVIGATION, javaClass.simpleName)
}