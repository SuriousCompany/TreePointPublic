package company.surious.data.firestore.mappers.plant_details

import company.surious.data.firestore.mappers.Mapper
import company.surious.data.firestore.models.plant_details.FirestoreTaxonomy
import company.surious.domain.entities.identification.result.details.Taxonomy

object FirestoreTaxonomyMapper : Mapper<FirestoreTaxonomy?, Taxonomy?> {

    override fun mapToEntity(model: FirestoreTaxonomy?): Taxonomy? =
        model?.run {
            Taxonomy(plantClass, family, genus, kingdom, order, phylum)
        }

    override fun mapToDataModel(entity: Taxonomy?): FirestoreTaxonomy? =
        entity?.run {
            FirestoreTaxonomy(plantClass, family, genus, kingdom, order, phylum)
        }
}