package company.surious.data.firestore.models.plant_details

data class FirestorePlantCommonNames(
    val eu: List<String>? = null,
    val ru: List<String>? = null,
    val uk: List<String>? = null
) {
    override fun toString(): String {
        return "eu: ${eu?.joinToString()}\nru: ${ru?.joinToString()}\nuk: ${uk?.joinToString()}"
    }
}