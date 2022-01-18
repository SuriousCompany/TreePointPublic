package company.surious.data.retrofit.mappers

import company.surious.data.retrofit.model.RetrofitIdentificationRequest
import company.surious.domain.entities.identification.HealthAssessmentMode
import company.surious.domain.entities.identification.IdentificationAccuracy
import company.surious.domain.entities.identification.IdentificationRequest

//TODO move all string resources to the constants class
object IdentificationRequestMapper {

    fun mapToRetrofitModel(entity: IdentificationRequest): RetrofitIdentificationRequest =
        with(entity) {
            RetrofitIdentificationRequest(
                images,
                lat,
                lng,
                createModifiers(accuracy, healthAssessmentMode, requestSimilarImages),
                createPlantDetails()
            )
        }

    private fun createModifiers(
        accuracy: IdentificationAccuracy,
        healthAssessmentMode: HealthAssessmentMode,
        requestSimilarImages: Boolean
    ): List<String> {
        val res = ArrayList<String>()
        res.add(mapAccuracy(accuracy))
        if (requestSimilarImages) {
            res.add("similar_images")
        }
        res.add(mapHealthAssessmentMode(healthAssessmentMode))
        return res
    }

    private fun mapAccuracy(accuracy: IdentificationAccuracy) =
        when (accuracy) {
            IdentificationAccuracy.DEFAULT -> "crops_fast"
            IdentificationAccuracy.LOW -> "crops_simple"
            IdentificationAccuracy.HIGH -> "crops_medium"
        }

    private fun mapHealthAssessmentMode(healthAssessmentMode: HealthAssessmentMode) =
        when (healthAssessmentMode) {
            HealthAssessmentMode.NEVER -> "health_probability"
            HealthAssessmentMode.AUTO -> "health_auto"
            HealthAssessmentMode.ALWAYS -> "health_all"
        }

    private fun createPlantDetails(): List<String> {
        val res = ArrayList<String>()
        res.add("common_names")
        res.add("wiki_description")
        res.add("edible_parts")
        res.add("gbif_id")
        res.add("propagation_methods")
        res.add("name_authority")
        //res.add("synonyms")
        res.add("taxonomy")
        res.add("url")
        //res.add("wiki_image")
        return res
    }

}