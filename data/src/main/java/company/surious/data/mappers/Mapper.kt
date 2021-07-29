package company.surious.data.mappers

interface Mapper<FirestoreModel, Entity> {
    fun mapToEntity(model: FirestoreModel): Entity
    fun mapToFirestoreModel(entity: Entity): FirestoreModel
}