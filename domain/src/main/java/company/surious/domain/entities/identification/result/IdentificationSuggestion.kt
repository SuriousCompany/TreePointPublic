package company.surious.domain.entities.identification.result

import company.surious.domain.entities.identification.result.details.PlantDetails

data class IdentificationSuggestion(
    val id: Int,
    val plantName: String,
    val plantDetails: PlantDetails,
    val probability: Float,
    val confirmed: Boolean
)