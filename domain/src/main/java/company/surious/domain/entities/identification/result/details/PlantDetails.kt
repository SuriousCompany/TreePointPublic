package company.surious.domain.entities.identification.result.details

import android.os.Parcelable
import company.surious.domain.types.Identifiable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class PlantDetails(
    val commonNames: PlantCommonNames = PlantCommonNames(),
    val wikiDescriptions: PlantWikiDescriptions = PlantWikiDescriptions(),
    val edibleParts: List<String>? = null,
    val gbifId: Int = -1,
    val propagationMethods: List<String>? = null,
    val nameAuthority: String? = null,
    val taxonomy: Taxonomy? = null,
    val urls: PlantUrls = PlantUrls(),
    val scientificName: String = "",
    val structuredName: PlantStructuredName = PlantStructuredName()
) : Identifiable<String>, Parcelable {
    override var id: String = gbifId.toString()

    fun getName() =
        (commonNames.en?.firstOrNull() ?: scientificName.ifEmpty { null } ?: structuredName.genus)
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
}