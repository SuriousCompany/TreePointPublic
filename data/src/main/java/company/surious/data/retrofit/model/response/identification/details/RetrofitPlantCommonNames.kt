package company.surious.data.retrofit.model.response.identification.details

import com.google.gson.annotations.SerializedName

data class RetrofitPlantCommonNames(
    @SerializedName("en")
    val en: List<String>?,
    @SerializedName("ru")
    val ru: List<String>?,
    @SerializedName("uk")
    val uk: List<String>?
)