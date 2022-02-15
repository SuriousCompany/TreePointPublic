package company.surious.domain.assemblers

import company.surious.domain.entities.identification.result.details.PlantDetails
import company.surious.domain.entities.plants.TreePoint
import company.surious.domain.errors.TreeError.NoItemInCollectionError
import company.surious.domain.repositories.PlantDetailsRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class TreePointAssembler(private val plantDetailsRepository: PlantDetailsRepository) {

    fun assemblePlantDetails(treePointsSource: Observable<List<TreePoint>>) =
        plantDetailsRepository.getAllPlantDetails()
            .subscribeOn(Schedulers.io())
            .flatMapObservable { treeTypes ->
                treePointsSource.map {
                    it.map { treePoint ->
                        map(treePoint, treeTypes)
                    }
                }
            }

    fun assemblePlantDetails(treePointsSource: Single<List<TreePoint>>) =
        plantDetailsRepository.getAllPlantDetails()
            .subscribeOn(Schedulers.io())
            .flatMap { treeTypes ->
                treePointsSource.map { treePoints ->
                    treePoints.map { treePoint ->
                        map(treePoint, treeTypes)
                    }
                }
            }

    fun assembleTreeType(treePointsSource: Observable<TreePoint>) =
        treePointsSource.flatMapSingle { treePoint ->
            plantDetailsRepository.getPlantDetailsById(treePoint.plant.gbifId)
                .subscribeOn(Schedulers.io())
                .map { type -> map(treePoint, type) }
        }

    fun assembleTreeType(treePointsSource: Single<TreePoint>) =
        treePointsSource.flatMap { treePoint ->
            plantDetailsRepository.getPlantDetailsById(treePoint.plant.gbifId)
                .subscribeOn(Schedulers.io())
                .map { type -> map(treePoint, type) }
        }

    private fun map(treePoint: TreePoint, plantDetails: List<PlantDetails>): TreePoint {
        val type = plantDetails.firstOrNull { it.gbifId == treePoint.plant.gbifId }
        return map(treePoint, type)
    }

    private fun map(treePoint: TreePoint, plantDetails: PlantDetails?): TreePoint {
        return if (plantDetails != null) {
            treePoint.plant = plantDetails
            treePoint
        } else {
            throw NoItemInCollectionError("PlantDetails", treePoint.plant.gbifId.toString())
        }
    }
}