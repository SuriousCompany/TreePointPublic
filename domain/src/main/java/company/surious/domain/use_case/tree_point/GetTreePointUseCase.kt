package company.surious.domain.use_case.tree_point

import company.surious.domain.assemblers.TreePointAssembler
import company.surious.domain.entities.TreePoint
import company.surious.domain.repositories.TreePointRepository
import company.surious.domain.use_case.base.SingleUseCase
import io.reactivex.Single

class GetTreePointUseCase(
    private val treePointRepository: TreePointRepository,
    private val treePointAssembler: TreePointAssembler
) : SingleUseCase<String, TreePoint>() {
    override fun createSingle(params: String): Single<TreePoint> =
        treePointAssembler.assembleTreeType(treePointRepository.getTreePoint(params))
}