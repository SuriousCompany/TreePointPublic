package company.surious.data.retrofit.mappers

import company.surious.data.retrofit.model.response.identification.*
import company.surious.data.retrofit.model.response.identification.details.*
import company.surious.data.retrofit.model.response.identification.health.RetrofitHealthAssessment
import company.surious.domain.entities.identification.result.*
import company.surious.domain.entities.identification.result.details.*
import company.surious.domain.entities.identification.result.health.HealthAssessment
import company.surious.domain.logging.logDebug

object IdentificationResultMapper {
    fun map(retrofitIdentificationResult: RetrofitIdentificationResult) =
        with(retrofitIdentificationResult) {
            IdentificationResult(
                id,
                customId,
                mapMetaData(metaData),
                uploadedDatetime,
                finishedDateTime,
                images.map(::mapImage),
                suggestions.map(::mapSuggestion),
                secret,
                failCause,
                countable,
                feedback,
                isPlantProbability,
                isPlant,
                mapHealthAssessment(healthAssessment)
            )
        }

    private fun mapMetaData(retrofitIdentificationMetaData: RetrofitIdentificationMetaData) =
        with(retrofitIdentificationMetaData) {
            IdentificationMetaData(lat, lng, date, dateTime)
        }

    private fun mapImage(retrofitIdentificationImage: RetrofitIdentificationImage) =
        with(retrofitIdentificationImage) {
            IdentificationImage(fileName, url)
        }

    private fun mapSuggestionImage(retrofitIdentificationSuggestionImage: RetrofitIdentificationSuggestionImage) =
        with(retrofitIdentificationSuggestionImage) {
            IdentificationSuggestionImage(id, similarity, url, smallImageUrl)
        }

    private fun mapSuggestion(retrofitIdentificationSuggestion: RetrofitIdentificationSuggestion) =
        with(retrofitIdentificationSuggestion) {
            IdentificationSuggestion(
                id,
                plantName,
                mapPlantDetails(plantDetails),
                probability,
                confirmed,
                images.map(::mapSuggestionImage)
            )
        }.apply {
            logDebug("mapped suggestion with ${retrofitIdentificationSuggestion.images.size} images to suggestion with ${images.size} images")
        }

    private fun mapPlantDetails(retrofitPlantDetails: RetrofitPlantDetails) =
        with(retrofitPlantDetails) {
            PlantDetails(
                mapCommonNames(commonNames),
                mapWikiDescriptions(wikiDescriptions),
                edibleParts,
                gbifId,
                propagationMethods,
                nameAuthority,
                mapTaxonomy(taxonomy),
                mapUrls(urls),
                scientificName,
                mapStructuredName(structuredName)
            )
        }

    private fun mapCommonNames(retrofitPlantCommonNames: RetrofitPlantCommonNames) =
        with(retrofitPlantCommonNames) {
            PlantCommonNames(en, ru, uk)
        }

    private fun mapWikiDescriptions(retrofitPlantWikiDescriptions: RetrofitPlantWikiDescriptions) =
        with(retrofitPlantWikiDescriptions) {
            PlantWikiDescriptions(
                mapWikiDescription(en),
                mapWikiDescription(ru),
                mapWikiDescription(uk)
            )
        }

    private fun mapWikiDescription(retrofitPlantWikiDescription: RetrofitPlantWikiDescription?) =
        retrofitPlantWikiDescription?.let {
            with(it) {
                PlantWikiDescription(value, citationUrl, licenseName, licenseUrl)
            }
        }

    private fun mapTaxonomy(retrofitTaxonomy: RetrofitTaxonomy?) =
        retrofitTaxonomy?.run { Taxonomy(plantClass, family, genus, kingdom, order, phylum) }

    private fun mapUrls(retrofitPlantUrls: RetrofitPlantUrls) =
        with(retrofitPlantUrls) {
            PlantUrls(en, ru, uk, global)
        }

    private fun mapStructuredName(retrofitPlantStructuredName: RetrofitPlantStructuredName) =
        with(retrofitPlantStructuredName) {
            PlantStructuredName(genus, species)
        }

    private fun mapHealthAssessment(retrofitHealthAssessment: RetrofitHealthAssessment) =
        with(retrofitHealthAssessment) {
            HealthAssessment(healthProbability, isHealthy)
        }
}