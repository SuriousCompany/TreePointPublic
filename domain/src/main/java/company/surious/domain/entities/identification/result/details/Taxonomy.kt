package company.surious.domain.entities.identification.result.details

data class Taxonomy(
    val plantClass: String,
    val family: String,
    val genus: String,
    val kingdom: String,
    val order: String,
    val phylum: String
)