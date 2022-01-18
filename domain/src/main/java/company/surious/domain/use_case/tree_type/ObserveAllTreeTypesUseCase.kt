package company.surious.domain.use_case.tree_type

import company.surious.domain.entities.plants.TreeType
import company.surious.domain.repositories.TreeTypeRepository
import company.surious.domain.use_case.base.ObservableUseCase
import io.reactivex.rxjava3.core.Observable

class ObserveAllTreeTypesUseCase(private val treeTypeRepository: TreeTypeRepository) :
    ObservableUseCase<Void?, List<TreeType>>() {

    override fun createObservable(params: Void?): Observable<List<TreeType>> =
        treeTypeRepository.observeAllTreeTypes()
}