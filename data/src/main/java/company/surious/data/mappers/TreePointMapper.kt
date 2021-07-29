package company.surious.data.mappers

import com.google.firebase.firestore.GeoPoint
import company.surious.data.models.FirestoreTreePoint
import company.surious.domain.entities.TreePoint
import company.surious.domain.entities.TreeType

object TreePointMapper : Mapper<FirestoreTreePoint, TreePoint> {
    override fun mapToEntity(model: FirestoreTreePoint): TreePoint =
        with(model) {
            TreePoint(
                id,
                geoPoint.latitude,
                geoPoint.longitude,
                creatorName,
                creatorId,
                ripeStartMonth,
                ripeEndMonth,
                creatorComment,
                TreeType(typeId)
            )
        }

    override fun mapToFirestoreModel(entity: TreePoint): FirestoreTreePoint =
        with(entity) {
            FirestoreTreePoint(
                id,
                GeoPoint(lat, lng),
                creatorName,
                creatorId,
                ripeStartMonth,
                ripeEndMonth,
                creatorComment,
                type.id
            )
        }
}