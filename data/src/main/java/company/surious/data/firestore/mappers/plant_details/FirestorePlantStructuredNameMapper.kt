package company.surious.data.firestore.mappers.plant_details

import company.surious.data.firestore.mappers.Mapper
import company.surious.data.firestore.models.plant_details.FirestorePlantStructuredName
import company.surious.domain.entities.identification.result.details.PlantStructuredName

object FirestorePlantStructuredNameMapper :
    Mapper<FirestorePlantStructuredName, PlantStructuredName> {
    override fun mapToEntity(model: FirestorePlantStructuredName): PlantStructuredName =
        model.run {
            PlantStructuredName(genus, species)
        }

    override fun mapToDataModel(entity: PlantStructuredName): FirestorePlantStructuredName =
        entity.run {
            FirestorePlantStructuredName(genus, species)
        }
}