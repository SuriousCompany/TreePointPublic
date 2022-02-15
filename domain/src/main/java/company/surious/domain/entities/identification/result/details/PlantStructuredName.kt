package company.surious.domain.entities.identification.result.details

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlantStructuredName(val genus: String = "", val species: String? = null) : Parcelable