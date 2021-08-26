package company.surious.domain.repositories

import android.net.Uri
import com.google.firebase.storage.StorageReference
import io.reactivex.Completable
import io.reactivex.Single

interface CloudStorageRepository {
    fun uploadTreePhoto(treePointId: String, photoUri: Uri): Completable
    fun uploadTreePhotos(treePointId: String, photos: List<Uri>): Completable
    fun downloadTreePhotos(treePointId: String): Single<List<StorageReference>>
}