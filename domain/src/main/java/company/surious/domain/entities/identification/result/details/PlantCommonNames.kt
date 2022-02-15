package company.surious.domain.entities.identification.result.details

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlantCommonNames(
    val en: List<String>? = null,
    val ru: List<String>? = null,
    val uk: List<String>? = null
) : Parcelable {
    override fun toString(): String {
        return "en: ${en?.joinToString()}\nru: ${ru?.joinToString()}\nuk: ${uk?.joinToString()}"
    }
}