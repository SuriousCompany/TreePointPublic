package company.surious.domain.entities.identification.result.details

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlantWikiDescriptions(
    val en: PlantWikiDescription? = null,
    val ru: PlantWikiDescription? = null,
    val uk: PlantWikiDescription? = null
) : Parcelable