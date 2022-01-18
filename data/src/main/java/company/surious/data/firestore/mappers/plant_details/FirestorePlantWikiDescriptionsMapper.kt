package company.surious.data.firestore.mappers.plant_details

import company.surious.data.firestore.mappers.Mapper
import company.surious.data.firestore.models.plant_details.FirestorePlantWikiDescriptions
import company.surious.domain.entities.identification.result.details.PlantWikiDescriptions

object FirestorePlantWikiDescriptionsMapper :
    Mapper<FirestorePlantWikiDescriptions, PlantWikiDescriptions> {
    override fun mapToEntity(model: FirestorePlantWikiDescriptions): PlantWikiDescriptions =
        model.run {
            PlantWikiDescriptions(
                eu?.let { FirestorePlantWikiDescriptionMapper.mapToEntity(it) },
                ru?.let { FirestorePlantWikiDescriptionMapper.mapToEntity(it) },
                uk?.let { FirestorePlantWikiDescriptionMapper.mapToEntity(it) }
            )
        }

    override fun mapToDataModel(entity: PlantWikiDescriptions): FirestorePlantWikiDescriptions =
        entity.run {
            FirestorePlantWikiDescriptions(
                eu?.let { FirestorePlantWikiDescriptionMapper.mapToDataModel(it) },
                ru?.let { FirestorePlantWikiDescriptionMapper.mapToDataModel(it) },
                uk?.let { FirestorePlantWikiDescriptionMapper.mapToDataModel(it) }
            )
        }
}