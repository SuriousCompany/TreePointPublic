package company.surious.domain.use_case.tree_type

import company.surious.domain.entities.TreeType
import company.surious.domain.repositories.TreeTypeRepository
import company.surious.domain.use_case.base.ObservableUseCase
import io.reactivex.Observable

class ObserveAllTreeTypesUseCase(private val treeTypeRepository: TreeTypeRepository) :
    ObservableUseCase<Void?, List<TreeType>>() {

    override fun createObservable(params: Void?): Observable<List<TreeType>> =
        treeTypeRepository.observeAllTreeTypes()
}