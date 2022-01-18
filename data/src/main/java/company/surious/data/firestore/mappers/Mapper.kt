package company.surious.data.firestore.mappers

interface Mapper<DataModel, Entity> {
    fun mapToEntity(model: DataModel): Entity
    fun mapToDataModel(entity: Entity): DataModel
}