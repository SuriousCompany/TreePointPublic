package company.surious.data.retrofit.model.response.identification

import com.google.gson.annotations.SerializedName

data class RetrofitIdentificationImage(
    @SerializedName("file_name")
    val fileName: String,
    @SerializedName("url")
    val url: String
)