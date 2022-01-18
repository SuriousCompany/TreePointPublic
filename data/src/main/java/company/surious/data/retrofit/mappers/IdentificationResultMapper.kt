package company.surious.data.retrofit.mappers

import company.surious.data.retrofit.model.response.identification.RetrofitIdentificationImage
import company.surious.data.retrofit.model.response.identification.RetrofitIdentificationMetaData
import company.surious.data.retrofit.model.response.identification.RetrofitIdentificationResult
import company.surious.data.retrofit.model.response.identification.RetrofitIdentificationSuggestion
import company.surious.data.retrofit.model.response.identification.details.*
import company.surious.data.retrofit.model.response.identification.health.RetrofitHealthAssessment
import company.surious.domain.entities.identification.result.IdentificationImage
import company.surious.domain.entities.identification.result.IdentificationMetaData
import company.surious.domain.entities.identification.result.IdentificationResult
import company.surious.domain.entities.identification.result.IdentificationSuggestion
import company.surious.domain.entities.identification.result.details.*
import company.surious.domain.entities.identification.result.health.HealthAssessment

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

    private fun mapSuggestion(retrofitIdentificationSuggestion: RetrofitIdentificationSuggestion) =
        with(retrofitIdentificationSuggestion) {
            IdentificationSuggestion(
                id,
                plantName,
                mapPlantDetails(plantDetails),
                probability,
                confirmed
            )
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
            PlantCommonNames(eu, ru, uk)
        }

    private fun mapWikiDescriptions(retrofitPlantWikiDescriptions: RetrofitPlantWikiDescriptions) =
        with(retrofitPlantWikiDescriptions) {
            PlantWikiDescriptions(
                mapWikiDescription(eu),
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

    private fun mapTaxonomy(retrofitTaxonomy: RetrofitTaxonomy) =
        with(retrofitTaxonomy) {
            Taxonomy(plantClass, family, genus, kingdom, order, phylum)
        }

    private fun mapUrls(retrofitPlantUrls: RetrofitPlantUrls) =
        with(retrofitPlantUrls) {
            PlantUrls(eu, ru, uk, global)
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