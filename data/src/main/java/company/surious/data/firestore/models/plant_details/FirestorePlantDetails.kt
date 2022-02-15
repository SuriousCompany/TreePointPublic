package company.surious.data.firestore.models.plant_details

data class FirestorePlantDetails(
    val commonNames: FirestorePlantCommonNames = FirestorePlantCommonNames(),
    val wikiDescriptions: FirestorePlantWikiDescriptions = FirestorePlantWikiDescriptions(),
    val edibleParts: List<String>? = null,
    val gbifId: Int = -1,
    val propagationMethods: List<String>? = null,
    val nameAuthority: String? = null,
    val taxonomy: FirestoreTaxonomy? = null,
    val urls: FirestorePlantUrls = FirestorePlantUrls(),
    val scientificName: String = "",
    val structuredName: FirestorePlantStructuredName = FirestorePlantStructuredName()
)