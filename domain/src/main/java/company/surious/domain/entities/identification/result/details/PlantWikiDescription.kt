package company.surious.domain.entities.identification.result.details

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlantWikiDescription(
    val value: String = "",
    val citationUrl: String = "",
    val licenseName: String = "",
    val licenseUrl: String = ""
) : Parcelable