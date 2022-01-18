package company.surious.domain.use_case.cloud_storage

import com.google.firebase.storage.StorageReference
import company.surious.domain.repositories.CloudStorageRepository
import company.surious.domain.use_case.base.SingleUseCase
import io.reactivex.rxjava3.core.Single

class DownloadTreePointPhotosUseCase(private val cloudStorageRepository: CloudStorageRepository) :
    SingleUseCase<String, List<StorageReference>>() {

    override fun createSingle(params: String): Single<List<StorageReference>> =
        cloudStorageRepository.downloadTreePhotos(params)
}