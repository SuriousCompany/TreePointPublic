package company.surious.data.firestore.models

import com.google.firebase.firestore.GeoPoint

data class FirestoreTreePoint(
    var id: String = "",
    val geoPoint: GeoPoint = GeoPoint(-1.0, -1.0),
    var creatorName: String = "",
    val creatorId: String = "",
    var ripeStartMonth: Int = -1,
    var ripeEndMonth: Int = -1,
    var creatorComment: String = "",
    var plantDetailsId: Int = -1,
    var plantNameEu: String = "",
    var isVerified: Boolean = false,
    var verifiedBy: String? = null,
    var creationDate: Long = -1,
    var updateDate: Long = -1,
    var verificationDate: Long = -1
)