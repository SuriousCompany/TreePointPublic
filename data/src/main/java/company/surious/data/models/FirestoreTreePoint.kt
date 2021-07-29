package company.surious.data.models

import com.google.firebase.firestore.GeoPoint

data class FirestoreTreePoint(
    var id: String = "",
    val geoPoint: GeoPoint = GeoPoint(-1.0, -1.0),
    var creatorName: String = "",
    val creatorId: String = "",
    var ripeStartMonth: Int = -1,
    var ripeEndMonth: Int = -1,
    var creatorComment: String = "",
    var typeId: String = ""
)