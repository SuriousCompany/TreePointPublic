package company.surious.treepoint.ui.common.view_models.photos

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import company.surious.domain.logging.logUnhandledError
import company.surious.domain.use_case.cloud_storage.TreePointPhotos
import company.surious.domain.use_case.cloud_storage.UploadTreePointPhotosUseCase
import company.surious.treepoint.ui.common.view_models.base.LoadingViewModel

class UploadPhotosViewModel(private val uploadUseCase: UploadTreePointPhotosUseCase) :
    LoadingViewModel() {

    companion object {
        const val NOT_UPLOADED = -1
    }

    override var isLoadingByDefault: Boolean = false

    val uploadingResult = MutableLiveData<Int>().apply { value = NOT_UPLOADED }

    fun uploadPhotos(treePointId: String, photos: List<Uri>) {
        disposables.add(
            uploadUseCase.execute(TreePointPhotos(treePointId, photos)).subscribe(
                {
                    isLoading.value = false
                    uploadingResult.value = photos.size
                },
                { error ->
                    isLoading.value = false
                    uploadingResult.value = NOT_UPLOADED
                    logUnhandledError(error, "uploadPhotos")
                })
        )
    }
}