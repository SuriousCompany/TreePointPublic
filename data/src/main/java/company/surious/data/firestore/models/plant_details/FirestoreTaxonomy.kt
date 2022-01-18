package company.surious.data.firestore.models.plant_details

data class FirestoreTaxonomy(
    val plantClass: String = "",
    val family: String = "",
    val genus: String = "",
    val kingdom: String = "",
    val order: String = "",
    val phylum: String = ""
)