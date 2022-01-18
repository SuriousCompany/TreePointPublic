package company.surious.data.firestore.mappers.plant_details

import company.surious.data.firestore.mappers.Mapper
import company.surious.data.firestore.models.plant_details.FirestorePlantWikiDescription
import company.surious.domain.entities.identification.result.details.PlantWikiDescription

object FirestorePlantWikiDescriptionMapper :
    Mapper<FirestorePlantWikiDescription, PlantWikiDescription> {

    override fun mapToEntity(model: FirestorePlantWikiDescription): PlantWikiDescription =
        model.run {
            PlantWikiDescription(value, citationUrl, licenseName, licenseUrl)
        }

    override fun mapToDataModel(entity: PlantWikiDescription): FirestorePlantWikiDescription =
        entity.run {
            FirestorePlantWikiDescription(value, citationUrl, licenseName, licenseUrl)
        }
}