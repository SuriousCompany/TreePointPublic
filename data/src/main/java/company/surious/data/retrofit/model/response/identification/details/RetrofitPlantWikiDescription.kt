package company.surious.data.retrofit.model.response.identification.details

import com.google.gson.annotations.SerializedName

data class RetrofitPlantWikiDescription(
    @SerializedName("value")
    val value: String,
    @SerializedName("citation")
    val citationUrl: String,
    @SerializedName("license_name")
    val licenseName: String,
    @SerializedName("license_url")
    val licenseUrl: String
)