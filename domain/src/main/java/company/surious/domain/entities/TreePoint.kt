package company.surious.domain.entities

import android.os.Parcelable
import company.surious.domain.types.Identifiable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TreePoint(
    override var id: String = "",
    var lat: Double = 0.0,
    var lng: Double = 0.0,
    var creatorName: String = "",
    val creatorId: String = "",
    var ripeStartMonth: Int = -1,
    var ripeEndMonth: Int = -1,
    var creatorComment: String = "",
    var type: TreeType = TreeType()
) : Parcelable, Identifiable<String>