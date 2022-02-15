package company.surious.data.retrofit.model.response.identification

import com.google.gson.annotations.SerializedName

data class RetrofitIdentificationSuggestionImage(
    @SerializedName("id")
    val id: String,
    @SerializedName("similarity")
    val similarity: Double,
    @SerializedName("url")
    val url: String,
    @SerializedName("url_small")
    val smallImageUrl: String
)