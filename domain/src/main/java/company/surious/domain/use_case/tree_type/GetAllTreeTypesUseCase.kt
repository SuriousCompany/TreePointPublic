package company.surious.domain.use_case.tree_type

import company.surious.domain.entities.TreeType
import company.surious.domain.repositories.TreeTypeRepository
import company.surious.domain.use_case.base.SingleUseCase
import io.reactivex.Single

class GetAllTreeTypesUseCase(private val treeTypeRepository: TreeTypeRepository) :
    SingleUseCase<Void?, List<TreeType>>() {

    override fun createSingle(params: Void?): Single<List<TreeType>> =
        treeTypeRepository.getAllTreeTypes()
}