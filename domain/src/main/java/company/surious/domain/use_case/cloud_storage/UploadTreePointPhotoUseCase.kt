package company.surious.domain.use_case.cloud_storage

import android.net.Uri
import company.surious.domain.repositories.CloudStorageRepository
import company.surious.domain.use_case.base.CompletableUseCase
import io.reactivex.Completable

class UploadTreePointPhotoUseCase(private val cloudStorageRepository: CloudStorageRepository) :
    CompletableUseCase<Uri>() {

    override fun createCompletable(params: Uri): Completable =
        cloudStorageRepository.uploadTreePhoto("treePointId", params)
}