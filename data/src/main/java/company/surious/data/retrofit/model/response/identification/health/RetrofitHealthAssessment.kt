package company.surious.data.retrofit.model.response.identification.health

import com.google.gson.annotations.SerializedName

data class RetrofitHealthAssessment(
    @SerializedName("is_healthy_probability")
    val healthProbability: Float,
    @SerializedName("is_healthy")
    val isHealthy: Boolean
)