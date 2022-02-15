package company.surious.domain.entities.identification.result

data class IdentificationSuggestionImage(
    val id: String,
    val similarity: Double,
    val url: String,
    val smallImageUrl: String
)