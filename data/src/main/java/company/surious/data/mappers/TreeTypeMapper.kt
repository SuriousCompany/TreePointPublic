package company.surious.data.mappers

import company.surious.data.models.FirestoreTreeType
import company.surious.domain.entities.TreeType

object TreeTypeMapper : Mapper<FirestoreTreeType, TreeType> {

    override fun mapToEntity(model: FirestoreTreeType) =
        with(model) {
            TreeType(id, typeName, typeNameRu, typeNameUa, iconUrl)
        }

    override fun mapToFirestoreModel(entity: TreeType) =
        with(entity) {
            FirestoreTreeType(id, typeName, typeNameRu, typeNameUa, iconUrl)
        }
}