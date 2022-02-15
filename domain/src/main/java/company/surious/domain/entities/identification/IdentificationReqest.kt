package company.surious.domain.entities.identification

data class IdentificationRequest(
    //TODO try to move Base64 conversion to data layer
    val images: List<String>,
    val healthAssessmentMode: HealthAssessmentMode,
    val accuracy: IdentificationAccuracy,
    val lat: Float? = null,
    val lng: Float? = null,
    val requestSimilarImages: Boolean = true
)