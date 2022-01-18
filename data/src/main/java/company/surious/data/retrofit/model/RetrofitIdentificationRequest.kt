package company.surious.data.retrofit.model

import com.google.gson.annotations.SerializedName
import company.surious.data.retrofit.model.IdentificationConstants.LANGUAGES

data class RetrofitIdentificationRequest(
    @SerializedName("images")
    val images: List<String>,
    @SerializedName("latitude")
    val lat: Float? = null,
    @SerializedName("longitude")
    val lng: Float? = null,
    @SerializedName("modifiers")
    val modifiers: List<String>,
    @SerializedName("plant_details")
    val plantDetails: List<String>,
    @SerializedName("plant_languages")
    val plantLanguages: List<String> = LANGUAGES,
    @SerializedName("custom_id")
    val id: Int = (System.currentTimeMillis() % 10000).toInt(),
    @SerializedName("datetime")
    val timeStampSeconds: Int = (System.currentTimeMillis() / 1000).toInt(),
    @SerializedName("identification_timeout")
    val timeoutSeconds: Int = 20
)