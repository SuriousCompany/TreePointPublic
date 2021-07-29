package company.surious.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TreePointDraft(
    var lat: Double = 0.0,
    var lng: Double = 0.0,
    var type: TreeType = TreeType(),
    var ripeStartMonth: Int = -1,
    var ripeEndMonth: Int = -1,
    var creatorComment: String = ""
) : Parcelable