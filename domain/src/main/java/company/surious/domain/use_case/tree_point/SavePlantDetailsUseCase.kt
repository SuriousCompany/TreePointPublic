package company.surious.domain.use_case.tree_point

import company.surious.domain.entities.identification.result.details.PlantDetails
import company.surious.domain.repositories.PlantDetailsRepository
import company.surious.domain.use_case.base.SingleUseCase
import io.reactivex.rxjava3.core.Single

class SavePlantDetailsUseCase(private val plantDetailsRepository: PlantDetailsRepository) :
    SingleUseCase<List<PlantDetails>, Int>() {

    override fun createSingle(params: List<PlantDetails>): Single<Int> =
        plantDetailsRepository.savePlantDetails(params)
}