package company.surious.domain.entities.identification.result.details

data class PlantCommonNames(
    val eu: List<String>?,
    val ru: List<String>?,
    val uk: List<String>?
) {
    override fun toString(): String {
        return "eu: $eu\nru: $ru\nuk: $uk"
    }
}