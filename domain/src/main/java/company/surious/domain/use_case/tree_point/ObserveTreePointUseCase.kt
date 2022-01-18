package company.surious.domain.use_case.tree_point

import company.surious.domain.assemblers.TreePointAssembler
import company.surious.domain.entities.plants.TreePoint
import company.surious.domain.repositories.TreePointRepository
import company.surious.domain.use_case.base.ObservableUseCase
import io.reactivex.rxjava3.core.Observable

class ObserveTreePointUseCase(
    private val treePointRepository: TreePointRepository,
    private val treePointAssembler: TreePointAssembler
) : ObservableUseCase<String, TreePoint>() {

    override fun createObservable(params: String): Observable<TreePoint> =
        treePointAssembler.assembleTreeType(treePointRepository.observeTreePoint(params))
}