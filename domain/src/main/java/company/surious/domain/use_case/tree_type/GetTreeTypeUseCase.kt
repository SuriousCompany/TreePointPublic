package company.surious.domain.use_case.tree_type

import company.surious.domain.entities.TreeType
import company.surious.domain.repositories.TreeTypeRepository
import company.surious.domain.use_case.base.SingleUseCase
import io.reactivex.Single

class GetTreeTypeUseCase(private val treeTypeRepository: TreeTypeRepository) :
    SingleUseCase<String, TreeType>() {
    override fun createSingle(params: String): Single<TreeType> =
        treeTypeRepository.getTreeType(params)
}