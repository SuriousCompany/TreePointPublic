package company.surious.data.firestore.mappers

import company.surious.data.firestore.models.FirestoreTreeType
import company.surious.domain.entities.plants.TreeType

object TreeTypeMapper : Mapper<FirestoreTreeType, TreeType> {

    override fun mapToEntity(model: FirestoreTreeType) =
        with(model) {
            TreeType(id, typeName, typeNameRu, typeNameUa, iconUrl)
        }

    override fun mapToDataModel(entity: TreeType) =
        with(entity) {
            FirestoreTreeType(id, typeName, typeNameRu, typeNameUa, iconUrl)
        }
}