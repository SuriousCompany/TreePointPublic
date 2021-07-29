package company.surious.treepoint.ui.common.view_models.tree_point

import company.surious.domain.entities.TreePoint
import company.surious.domain.use_case.tree_point.GetTreePointUseCase
import company.surious.domain.use_case.tree_point.ObserveTreePointUseCase
import company.surious.treepoint.ui.common.view_models.base.ObservableViewModel

class TreePointViewModel(
    getTreePointUseCase: GetTreePointUseCase,
    observeTreePointUseCase: ObserveTreePointUseCase
) : ObservableViewModel<String, TreePoint>() {

    override val observableUseCase = observeTreePointUseCase
    override val refreshingUseCase = getTreePointUseCase
}