package company.surious.domain.repositories

import android.net.Uri
import io.reactivex.Completable

interface CloudStorageRepository {
    fun uploadTreePhoto(treePointId: String, photoUri: Uri): Completable
}