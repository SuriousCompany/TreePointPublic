package company.surious.domain.use_case.cloud_storage

import company.surious.domain.repositories.CloudStorageRepository
import company.surious.domain.use_case.base.CompletableUseCase
import io.reactivex.Completable

class UploadTreePointPhotoUseCase(private val cloudStorageRepository: CloudStorageRepository) :
    CompletableUseCase<TreePointPhoto>() {

    override fun createCompletable(params: TreePointPhoto): Completable =
        cloudStorageRepository.uploadTreePhoto(params.treePointId, params.uri)
}