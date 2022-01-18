package company.surious.data.retrofit.model.response.identification

import com.google.gson.annotations.SerializedName
import company.surious.data.retrofit.model.response.identification.health.RetrofitHealthAssessment

data class RetrofitIdentificationResult(
    @SerializedName("id")
    val id: Int,
    @SerializedName("custom_id")
    val customId: Int,
    @SerializedName("meta_data")
    val metaData: RetrofitIdentificationMetaData,
    @SerializedName("uploaded_datetime")
    val uploadedDatetime: Float,
    @SerializedName("finished_datetime")
    val finishedDateTime: Float,
    @SerializedName("images")
    val images: List<RetrofitIdentificationImage>,
    @SerializedName("suggestions")
    val suggestions: List<RetrofitIdentificationSuggestion>,
    @SerializedName("secret")
    val secret: String,
    @SerializedName("fail_cause")
    val failCause: String?,
    @SerializedName("countable")
    val countable: Boolean,
    @SerializedName("feedback")
    val feedback: String?,
    @SerializedName("is_plant_probability")
    val isPlantProbability: Float,
    @SerializedName("is_plant")
    val isPlant: Boolean,
    @SerializedName("health_assessment")
    val healthAssessment: RetrofitHealthAssessment
)
