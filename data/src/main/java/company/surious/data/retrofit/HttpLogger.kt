package company.surious.data.retrofit

import company.surious.domain.logging.LogTags
import company.surious.domain.logging.logVerbose
import okhttp3.logging.HttpLoggingInterceptor

object HttpLogger : HttpLoggingInterceptor.Logger {
    override fun log(message: String) {
        logVerbose(LogTags.HTTP, message)
    }
}