package company.surious.data.firestore.mappers.plant_details

import company.surious.data.firestore.mappers.Mapper
import company.surious.data.firestore.models.plant_details.FirestorePlantCommonNames
import company.surious.domain.entities.identification.result.details.PlantCommonNames

object FirestorePlantCommonNamesMapper : Mapper<FirestorePlantCommonNames, PlantCommonNames> {
    override fun mapToDataModel(entity: PlantCommonNames): FirestorePlantCommonNames =
        entity.run {
            FirestorePlantCommonNames(eu, ru, uk)
        }

    override fun mapToEntity(model: FirestorePlantCommonNames): PlantCommonNames =
        model.run {
            PlantCommonNames(eu, ru, uk)
        }
}