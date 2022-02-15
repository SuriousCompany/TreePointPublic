package company.surious.domain.entities.plants

import android.os.Parcelable
import company.surious.domain.entities.identification.result.details.PlantDetails
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
    var plant: PlantDetails = PlantDetails(),
    var isVerified: Boolean = false,
    var verifiedBy: String? = null,
    var creationDate: Long = -1,
    var updateDate: Long = -1,
    var verificationDate: Long = -1
) : Parcelable, Identifiable<String> {

    val hasRipeSeason: Boolean
        get() = ripeStartMonth != -1 || ripeEndMonth != -1

}