package company.surious.domain.repositories

import company.surious.domain.entities.identification.result.details.PlantDetails
import io.reactivex.rxjava3.core.Single

interface PlantDetailsRepository {

    /**
     * returns count of the new plant details
     */
    fun savePlantDetails(details: List<PlantDetails>): Single<Int>

    fun getAllPlantDetails(): Single<List<PlantDetails>>

    fun getPlantDetailsById(id: Int): Single<PlantDetails>
}