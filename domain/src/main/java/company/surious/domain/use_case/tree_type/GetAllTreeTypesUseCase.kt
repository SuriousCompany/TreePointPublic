package company.surious.domain.use_case.tree_type

import company.surious.domain.entities.plants.TreeType
import company.surious.domain.repositories.TreeTypeRepository
import company.surious.domain.use_case.base.SingleUseCase
import io.reactivex.rxjava3.core.Single

class GetAllTreeTypesUseCase(private val treeTypeRepository: TreeTypeRepository) :
    SingleUseCase<Void?, List<TreeType>>() {

    override fun createSingle(params: Void?): Single<List<TreeType>> =
        treeTypeRepository.getAllTreeTypes()
}