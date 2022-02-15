package company.surious.data.retrofit.model.response.identification

import com.google.gson.annotations.SerializedName
import company.surious.data.retrofit.model.response.identification.details.RetrofitPlantDetails

data class RetrofitIdentificationSuggestion(
    @SerializedName("id")
    val id: Int,
    @SerializedName("plant_name")
    val plantName: String,
    @SerializedName("plant_details")
    val plantDetails: RetrofitPlantDetails,
    @SerializedName("probability")
    val probability: Float,
    @SerializedName("confirmed")
    val confirmed: Boolean,
    @SerializedName("similar_images")
    val images: List<RetrofitIdentificationSuggestionImage>
)