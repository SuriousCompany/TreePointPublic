package company.surious.data.retrofit.model.response.identification.details

import com.google.gson.annotations.SerializedName

data class RetrofitPlantUrls(
    @SerializedName("en")
    val en: String?,
    @SerializedName("ru")
    val ru: String?,
    @SerializedName("uk")
    val uk: String?,
    @SerializedName("global")
    val global: String?
)