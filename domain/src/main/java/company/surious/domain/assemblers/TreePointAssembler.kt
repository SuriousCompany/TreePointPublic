package company.surious.domain.assemblers

import company.surious.domain.entities.TreePoint
import company.surious.domain.entities.TreeType
import company.surious.domain.errors.NoItemInCollectionError
import company.surious.domain.repositories.TreeTypeRepository
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class TreePointAssembler(private val treeTypeRepository: TreeTypeRepository) {

    fun assembleTreeTypes(treePointsSource: Observable<List<TreePoint>>) =
        treeTypeRepository.getAllTreeTypes()
            .subscribeOn(Schedulers.io())
            .flatMapObservable { treeTypes ->
                treePointsSource.map {
                    it.map { treePoint ->
                        map(treePoint, treeTypes)
                    }
                }
            }

    fun assembleTreeTypes(treePointsSource: Single<List<TreePoint>>) =
        treeTypeRepository.getAllTreeTypes()
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
            treeTypeRepository.getTreeType(treePoint.type.id)
                .subscribeOn(Schedulers.io())
                .map { type -> map(treePoint, type) }
        }

    fun assembleTreeType(treePointsSource: Single<TreePoint>) =
        treePointsSource.flatMap { treePoint ->
            treeTypeRepository.getTreeType(treePoint.type.id)
                .subscribeOn(Schedulers.io())
                .map { type -> map(treePoint, type) }
        }

    private fun map(treePoint: TreePoint, treeTypes: List<TreeType>): TreePoint {
        val type = treeTypes.firstOrNull { it.id == treePoint.type.id }
        return map(treePoint, type)
    }

    private fun map(treePoint: TreePoint, treeType: TreeType?): TreePoint {
        return if (treeType != null) {
            treePoint.type = treeType
            treePoint
        } else {
            throw NoItemInCollectionError("TreeType", treePoint.type.id)
        }
    }
}