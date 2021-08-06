package company.surious.treepoint.ui.common.view_models.photos

import com.google.firebase.storage.StorageReference
import company.surious.domain.use_case.cloud_storage.DownloadTreePointPhotosUseCase
import company.surious.treepoint.ui.common.view_models.base.RefreshingViewModel

class TreePointPhotosViewModel(downloadTreePointPhotosUseCase: DownloadTreePointPhotosUseCase) :
    RefreshingViewModel<String, List<StorageReference>>() {
    override val refreshingUseCase = downloadTreePointPhotosUseCase
    override val defaultData: List<StorageReference> = ArrayList()

    val photos = dataSource

    fun refreshPhotos(treePointId: String) {
        refresh(treePointId)
    }
}