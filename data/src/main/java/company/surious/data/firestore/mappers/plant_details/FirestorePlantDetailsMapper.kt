package company.surious.data.firestore.mappers.plant_details

import company.surious.data.firestore.mappers.Mapper
import company.surious.data.firestore.models.plant_details.FirestorePlantDetails
import company.surious.domain.entities.identification.result.details.PlantDetails

object FirestorePlantDetailsMapper : Mapper<FirestorePlantDetails, PlantDetails> {

    override fun mapToEntity(model: FirestorePlantDetails): PlantDetails =
        model.run {
            PlantDetails(
                FirestorePlantCommonNamesMapper.mapToEntity(commonNames),
                FirestorePlantWikiDescriptionsMapper.mapToEntity(wikiDescriptions),
                edibleParts,
                gbifId,
                propagationMethods,
                nameAuthority,
                FirestoreTaxonomyMapper.mapToEntity(taxonomy),
                FirestorePlantUrlsMapper.mapToEntity(urls),
                scientificName,
                FirestorePlantStructuredNameMapper.mapToEntity(structuredName)
            )
        }

    override fun mapToDataModel(entity: PlantDetails): FirestorePlantDetails =
        entity.run {
            FirestorePlantDetails(
                FirestorePlantCommonNamesMapper.mapToDataModel(commonNames),
                FirestorePlantWikiDescriptionsMapper.mapToDataModel(wikiDescriptions),
                edibleParts,
                gbifId,
                propagationMethods,
                nameAuthority,
                FirestoreTaxonomyMapper.mapToDataModel(taxonomy),
                FirestorePlantUrlsMapper.mapToDataModel(urls),
                scientificName,
                FirestorePlantStructuredNameMapper.mapToDataModel(structuredName)
            )
        }
}