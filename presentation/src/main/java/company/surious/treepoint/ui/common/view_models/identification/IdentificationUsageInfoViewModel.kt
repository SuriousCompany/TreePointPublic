package company.surious.treepoint.ui.common.view_models.identification

import androidx.lifecycle.LiveData
import company.surious.domain.entities.identification.IdentificationUsageInfo
import company.surious.domain.use_case.identification.GetUsageInfoUseCase
import company.surious.treepoint.ui.common.view_models.base.RefreshingViewModel

class IdentificationUsageInfoViewModel(getUsageInfoUseCase: GetUsageInfoUseCase) :
    RefreshingViewModel<Void?, IdentificationUsageInfo>() {

    override val refreshingUseCase = getUsageInfoUseCase

    val usageInfo: LiveData<IdentificationUsageInfo> = dataSource

    fun refreshUsageInfo() {
        refresh(null)
    }
}