package company.surious.data.retrofit.model.response.identification.details

import com.google.gson.annotations.SerializedName

data class RetrofitPlantWikiDescriptions(
    @SerializedName("eu")
    val eu: RetrofitPlantWikiDescription?,
    @SerializedName("ru")
    val ru: RetrofitPlantWikiDescription?,
    @SerializedName("uk")
    val uk: RetrofitPlantWikiDescription?
)