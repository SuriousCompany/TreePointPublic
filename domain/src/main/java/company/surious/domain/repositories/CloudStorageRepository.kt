package company.surious.domain.repositories

import android.net.Uri
import com.google.firebase.storage.StorageReference
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface CloudStorageRepository {
    fun uploadTreePhoto(treePointId: String, photoUri: Uri): Completable
    fun uploadTreePhotos(treePointId: String, photos: List<Uri>): Completable
    fun downloadTreePhotos(treePointId: String): Single<List<StorageReference>>
}