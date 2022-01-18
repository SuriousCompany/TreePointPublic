package company.surious.treepoint.ui.common.view_models.tree_point

import androidx.lifecycle.LiveData
import company.surious.domain.entities.plants.TreePoint
import company.surious.domain.use_case.tree_point.GetAllTreePointsUseCase
import company.surious.domain.use_case.tree_point.ObserveAllTreePointsUseCase
import company.surious.treepoint.ui.common.view_models.base.ObservableViewModel

class AllTreePointsViewModel(
    observeAllTreePointsUseCase: ObserveAllTreePointsUseCase,
    getAllTreePointsUseCase: GetAllTreePointsUseCase
) : ObservableViewModel<Void?, List<TreePoint>>() {

    override val defaultData: List<TreePoint> = ArrayList()
    override val observableUseCase = observeAllTreePointsUseCase
    override val refreshingUseCase = getAllTreePointsUseCase

    val treePoints = dataSource as LiveData<List<TreePoint>>

    fun refresh() {
        refresh(null)
    }

    fun startObserving() {
        startObserving(null)
    }
}