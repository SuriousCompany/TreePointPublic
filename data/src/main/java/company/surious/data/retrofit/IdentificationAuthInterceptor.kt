package company.surious.data.retrofit

import company.surious.data.config.DataConfig
import okhttp3.Interceptor
import okhttp3.Response

object IdentificationAuthInterceptor : Interceptor {
    const val AUTH_HEADER = "Api-Key"

    override fun intercept(chain: Interceptor.Chain): Response =
        chain.proceed(
            chain.request()
                .newBuilder()
                .addHeader(AUTH_HEADER, DataConfig.getPlantIdToken())
                .build()
        )

}