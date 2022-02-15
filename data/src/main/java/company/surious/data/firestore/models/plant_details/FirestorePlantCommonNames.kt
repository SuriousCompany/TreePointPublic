package company.surious.data.firestore.models.plant_details

data class FirestorePlantCommonNames(
    val en: List<String>? = null,
    val ru: List<String>? = null,
    val uk: List<String>? = null
) {
    override fun toString(): String {
        return "en: ${en?.joinToString()}\nru: ${ru?.joinToString()}\nuk: ${uk?.joinToString()}"
    }
}