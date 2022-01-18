package company.surious.domain.use_case.cloud_storage

import company.surious.domain.repositories.CloudStorageRepository
import company.surious.domain.use_case.base.CompletableUseCase
import io.reactivex.rxjava3.core.Completable

class UploadTreePointPhotosUseCase(private val cloudStorageRepository: CloudStorageRepository) :
    CompletableUseCase<TreePointPhotos>() {

    override fun createCompletable(params: TreePointPhotos): Completable =
        cloudStorageRepository.uploadTreePhotos(params.treePointId, params.uriList)
}