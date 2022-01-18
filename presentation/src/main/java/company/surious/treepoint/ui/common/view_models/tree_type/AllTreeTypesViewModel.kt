package company.surious.treepoint.ui.common.view_models.tree_type

import androidx.lifecycle.LiveData
import company.surious.domain.entities.plants.TreeType
import company.surious.domain.use_case.tree_type.GetAllTreeTypesUseCase
import company.surious.domain.use_case.tree_type.ObserveAllTreeTypesUseCase
import company.surious.treepoint.ui.common.view_models.base.ObservableViewModel

class AllTreeTypesViewModel(
    getAllTreeTypesUseCase: GetAllTreeTypesUseCase,
    observeAllTreeTypesUseCase: ObserveAllTreeTypesUseCase
) : ObservableViewModel<Void?, List<TreeType>>() {

    override val observableUseCase = observeAllTreeTypesUseCase
    override val refreshingUseCase = getAllTreeTypesUseCase
    override val defaultData: List<TreeType> = ArrayList()

    val treeTypes = dataSource as LiveData<List<TreeType>>

    fun startObserving() {
        startObserving(null)
    }
}