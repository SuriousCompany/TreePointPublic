package company.surious.domain.entities.identification.result.details

import company.surious.domain.types.Identifiable

data class PlantDetails(
    val commonNames: PlantCommonNames,
    val wikiDescriptions: PlantWikiDescriptions,
    val edibleParts: List<String>?,
    val gbifId: Int,
    val propagationMethods: List<String>?,
    val nameAuthority: String?,
    val taxonomy: Taxonomy,
    val urls: PlantUrls,
    val scientificName: String,
    val structuredName: PlantStructuredName
) : Identifiable<String> {
    override var id: String = gbifId.toString()
}