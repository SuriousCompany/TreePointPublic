package company.surious.domain.entities.identification.result.details

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlantUrls(
    val en: String? = null,
    val ru: String? = null,
    val uk: String? = null,
    val global: String? = null
) : Parcelable