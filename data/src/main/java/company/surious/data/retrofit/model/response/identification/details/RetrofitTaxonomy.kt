package company.surious.data.retrofit.model.response.identification.details

import com.google.gson.annotations.SerializedName

data class RetrofitTaxonomy(
    @SerializedName("class")
    val plantClass: String,
    @SerializedName("family")
    val family: String,
    @SerializedName("genus")
    val genus: String,
    @SerializedName("kingdom")
    val kingdom: String,
    @SerializedName("order")
    val order: String,
    @SerializedName("phylum")
    val phylum: String
)