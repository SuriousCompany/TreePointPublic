package company.surious.data.firestore.mappers.plant_details

import company.surious.data.firestore.mappers.Mapper
import company.surious.data.firestore.models.plant_details.FirestorePlantUrls
import company.surious.domain.entities.identification.result.details.PlantUrls

object FirestorePlantUrlsMapper : Mapper<FirestorePlantUrls, PlantUrls> {
    override fun mapToEntity(model: FirestorePlantUrls): PlantUrls =
        model.run {
            PlantUrls(eu, ru, uk, global)
        }

    override fun mapToDataModel(entity: PlantUrls): FirestorePlantUrls =
        entity.run {
            FirestorePlantUrls(eu, ru, uk, global)
        }
}