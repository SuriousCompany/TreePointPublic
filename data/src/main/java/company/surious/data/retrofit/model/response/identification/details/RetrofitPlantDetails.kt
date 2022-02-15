package company.surious.data.retrofit.model.response.identification.details

import com.google.gson.annotations.SerializedName

data class RetrofitPlantDetails(
    @SerializedName("common_names")
    val commonNames: RetrofitPlantCommonNames,
    @SerializedName("wiki_description")
    val wikiDescriptions: RetrofitPlantWikiDescriptions,
    @SerializedName("edible_parts")
    val edibleParts: List<String>?,
    @SerializedName("gbif_id")
    val gbifId: Int,
    @SerializedName("propagation_methods")
    val propagationMethods: List<String>?,
    @SerializedName("name_authority")
    val nameAuthority: String?,
    @SerializedName("taxonomy")
    val taxonomy: RetrofitTaxonomy? = null,
    @SerializedName("url")
    val urls: RetrofitPlantUrls,
    @SerializedName("scientific_name")
    val scientificName: String,
    @SerializedName("structured_name")
    val structuredName: RetrofitPlantStructuredName
)