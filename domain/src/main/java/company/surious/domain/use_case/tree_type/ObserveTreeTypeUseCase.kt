package company.surious.domain.use_case.tree_type

import company.surious.domain.entities.TreeType
import company.surious.domain.repositories.TreeTypeRepository
import company.surious.domain.use_case.base.ObservableUseCase
import io.reactivex.Observable

class ObserveTreeTypeUseCase(private val treeTypeRepository: TreeTypeRepository) :
    ObservableUseCase<String, TreeType>() {

    override fun createObservable(params: String): Observable<TreeType> =
        treeTypeRepository.observeTreeType(params)

}