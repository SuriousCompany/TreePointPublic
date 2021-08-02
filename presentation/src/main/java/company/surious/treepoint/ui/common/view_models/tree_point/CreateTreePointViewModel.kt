package company.surious.treepoint.ui.common.view_models.tree_point

import company.surious.domain.entities.TreePointDraft
import company.surious.domain.logging.logUnhandledError
import company.surious.domain.use_case.tree_point.CreateTreePointUseCase
import company.surious.treepoint.ui.common.view_models.base.LoadingViewModel

class CreateTreePointViewModel(private val createTreePointUseCase: CreateTreePointUseCase) :
    LoadingViewModel() {

    override var isLoadingByDefault: Boolean = false

    fun create(treePoint: TreePointDraft) {
        isLoading.value = true
        disposables.add(createTreePointUseCase.execute(treePoint).subscribe(
            {
                isLoading.value = false
            },
            {
                logUnhandledError(it, "TreePoint creation error")
            }
        ))
    }
}