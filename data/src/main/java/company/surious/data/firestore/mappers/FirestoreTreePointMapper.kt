package company.surious.data.firestore.mappers

import com.google.firebase.firestore.GeoPoint
import company.surious.data.firestore.models.FirestoreTreePoint
import company.surious.domain.entities.identification.result.details.PlantDetails
import company.surious.domain.entities.plants.TreePoint

object FirestoreTreePointMapper : Mapper<FirestoreTreePoint, TreePoint> {
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
                PlantDetails(gbifId = plantDetailsId),
                isVerified,
                verifiedBy,
                creationDate,
                updateDate,
                verificationDate
            )
        }

    override fun mapToDataModel(entity: TreePoint): FirestoreTreePoint =
        with(entity) {
            FirestoreTreePoint(
                id,
                GeoPoint(lat, lng),
                creatorName,
                creatorId,
                ripeStartMonth,
                ripeEndMonth,
                creatorComment,
                plant.gbifId,
                plant.getName(),
                isVerified,
                verifiedBy,
                creationDate,
                updateDate,
                verificationDate
            )
        }
}