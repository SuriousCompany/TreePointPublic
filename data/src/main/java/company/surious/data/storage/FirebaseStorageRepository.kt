package company.surious.data.storage

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import company.surious.domain.extensions.safeOnComplete
import company.surious.domain.extensions.safeOnError
import company.surious.domain.repositories.CloudStorageRepository
import io.reactivex.Completable
import io.reactivex.disposables.Disposables

class FirebaseStorageRepository(private val storage: FirebaseStorage) : CloudStorageRepository {

    override fun uploadTreePhoto(treePointId: String, photoUri: Uri): Completable {
        val treePointsReference = storage.getReference(StorageContract.TREE_POINTS)
        val photoReference = treePointsReference.child(photoUri.lastPathSegment!!)
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
}