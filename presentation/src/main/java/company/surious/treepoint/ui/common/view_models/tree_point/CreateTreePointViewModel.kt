package company.surious.treepoint.ui.common.view_models.tree_point

import androidx.lifecycle.MutableLiveData
import company.surious.domain.entities.plants.TreePointDraft
import company.surious.domain.logging.logUnhandledError
import company.surious.domain.use_case.tree_point.CreateTreePointUseCase
import company.surious.treepoint.ui.common.view_models.base.LoadingViewModel

class CreateTreePointViewModel(private val createTreePointUseCase: CreateTreePointUseCase) :
    LoadingViewModel() {

    override var isLoadingByDefault: Boolean = false

    val createdTreePointId = MutableLiveData<String?>().apply { value = null }

    fun create(treePoint: TreePointDraft) {
        isLoading.value = true
        disposables.add(
            createTreePointUseCase.execute(treePoint).subscribe(
                {
                    isLoading.value = false
                    createdTreePointId.value = it
                },
                {
                    logUnhandledError(it, "TreePoint creation error")
                    createdTreePointId.value = null
            }
        ))
    }
}