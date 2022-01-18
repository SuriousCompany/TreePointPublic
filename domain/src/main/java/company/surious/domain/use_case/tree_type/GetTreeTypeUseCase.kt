package company.surious.domain.use_case.tree_type

import company.surious.domain.entities.plants.TreeType
import company.surious.domain.repositories.TreeTypeRepository
import company.surious.domain.use_case.base.SingleUseCase
import io.reactivex.rxjava3.core.Single

class GetTreeTypeUseCase(private val treeTypeRepository: TreeTypeRepository) :
    SingleUseCase<String, TreeType>() {
    override fun createSingle(params: String): Single<TreeType> =
        treeTypeRepository.getTreeType(params)
}