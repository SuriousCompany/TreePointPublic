package company.surious.domain.use_case.tree_point

import company.surious.domain.assemblers.TreePointAssembler
import company.surious.domain.entities.plants.TreePoint
import company.surious.domain.repositories.TreePointRepository
import company.surious.domain.use_case.base.SingleUseCase
import io.reactivex.rxjava3.core.Single

class GetAllTreePointsUseCase(
    private val treePointRepository: TreePointRepository,
    private val treePointAssembler: TreePointAssembler
) : SingleUseCase<Void?, List<TreePoint>>() {

    override fun createSingle(params: Void?): Single<List<TreePoint>> =
        treePointAssembler.assemblePlantDetails(treePointRepository.getAllTreePoints())
}