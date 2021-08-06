package company.surious.data.storage

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import company.surious.domain.extensions.safeOnComplete
import company.surious.domain.extensions.safeOnError
import company.surious.domain.repositories.CloudStorageRepository
import io.reactivex.Completable
import io.reactivex.disposables.Disposables

class FirebaseStorageRepository(private val storage: FirebaseStorage) : CloudStorageRepository {
    private val treePointsReference by lazy {
        storage.getReference(StorageContract.TREE_POINTS)
    }

    override fun uploadTreePhoto(treePointId: String, photoUri: Uri): Completable {
        val treePointReference = treePointsReference.child(treePointId)
        val photoReference = treePointReference.child(photoUri.lastPathSegment!!)
        return Completable.create { emitter ->
            val task = photoReference.putFile(photoUri)
                .addOnFailureListener {
                    emitter.safeOnError(it)
                }.addOnSuccessListener { taskSnapshot ->
                    emitter.safeOnComplete()
                }
            emitter.setDisposable(Disposables.fromAction { task.cancel() })
        }
    }

    override fun uploadTreePhotos(treePointId: String, photos: List<Uri>): Completable =
        Completable.concat(photos.map { uploadTreePhoto(treePointId, it) })
}