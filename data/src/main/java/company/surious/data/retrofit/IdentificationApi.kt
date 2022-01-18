package company.surious.data.retrofit

import company.surious.data.retrofit.model.RetrofitIdentificationRequest
import company.surious.data.retrofit.model.response.RetrofitIdentificationUsageInfo
import company.surious.data.retrofit.model.response.identification.RetrofitIdentificationResult
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface IdentificationApi {
    @GET("usage_info")
    fun getUsageInfo(): Single<RetrofitIdentificationUsageInfo>

    @POST("identify")
    fun identify(@Body request: RetrofitIdentificationRequest): Single<RetrofitIdentificationResult>
}