package company.surious.domain.entities.identification.result

import company.surious.domain.entities.identification.result.health.HealthAssessment

data class IdentificationResult(
    val id: Int,
    val customId: Int,
    val metaData: IdentificationMetaData,
    val uploadedDatetime: Float,
    val finishedDateTime: Float,
    val images: List<IdentificationImage>,
    val suggestions: List<IdentificationSuggestion>,
    val secret: String,
    val failCause: String?,
    val countable: Boolean,
    val feedback: String?,
    val isPlantProbability: Float,
    val isPlant: Boolean,
    val healthAssessment: HealthAssessment
)
