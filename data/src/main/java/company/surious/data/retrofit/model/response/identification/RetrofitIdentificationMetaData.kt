package company.surious.data.retrofit.model.response.identification

import com.google.gson.annotations.SerializedName

data class RetrofitIdentificationMetaData(
    @SerializedName("latitude")
    val lat: Float?,
    @SerializedName("longitude")
    val lng: Float?,
    @SerializedName("date")
    val date: String,
    @SerializedName("datetime")
    val dateTime: String
)