package company.surious.data.retrofit.model.response.identification.details

import com.google.gson.annotations.SerializedName

data class RetrofitPlantStructuredName(
    @SerializedName("genus")
    val genus: String,
    @SerializedName("species")
    val species: String?
)