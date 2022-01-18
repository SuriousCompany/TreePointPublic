package company.surious.domain.use_case.tree_type

import company.surious.domain.entities.plants.TreeType
import company.surious.domain.repositories.TreeTypeRepository
import company.surious.domain.use_case.base.ObservableUseCase
import io.reactivex.rxjava3.core.Observable

class ObserveTreeTypeUseCase(private val treeTypeRepository: TreeTypeRepository) :
    ObservableUseCase<String, TreeType>() {

    override fun createObservable(params: String): Observable<TreeType> =
        treeTypeRepository.observeTreeType(params)

}