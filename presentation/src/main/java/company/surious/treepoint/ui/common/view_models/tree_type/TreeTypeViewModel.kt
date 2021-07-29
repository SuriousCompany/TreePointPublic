package company.surious.treepoint.ui.common.view_models.tree_type

import androidx.lifecycle.LiveData
import company.surious.domain.entities.TreeType
import company.surious.domain.use_case.tree_type.GetTreeTypeUseCase
import company.surious.domain.use_case.tree_type.ObserveTreeTypeUseCase
import company.surious.treepoint.ui.common.view_models.base.ObservableViewModel

class TreeTypeViewModel(
    getTreeTypeUseCase: GetTreeTypeUseCase,
    observeTreeTypeUseCase: ObserveTreeTypeUseCase
) : ObservableViewModel<String, TreeType>() {

    override val observableUseCase = observeTreeTypeUseCase
    override val refreshingUseCase = getTreeTypeUseCase

    val treeType = dataSource as LiveData<TreeType?>

}