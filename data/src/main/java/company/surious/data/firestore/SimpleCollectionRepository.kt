package company.surious.data.firestore

import com.google.firebase.firestore.FirebaseFirestore
import company.surious.data.extensions.*
import company.surious.data.firestore.mappers.Mapper
import company.surious.domain.errors.TreeError.CommunicationError.DetailedFirestoreError
import company.surious.domain.errors.TreeError.CommunicationError.FirestoreError
import company.surious.domain.extensions.mapErrors
import company.surious.domain.types.Identifiable
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import kotlin.reflect.KClass

abstract class SimpleCollectionRepository<FirestoreModel : Any, Entity : Identifiable<String>>(
    protected val firebaseFirestore: FirebaseFirestore
) {
    protected abstract val collectionName: String
    protected abstract val firestoreModelClass: KClass<FirestoreModel>
    protected abstract val mapper: Mapper<FirestoreModel, Entity>

    fun createWithNewId(item: Entity): Single<String> {
        val newDocument = firebaseFirestore.collection(collectionName).document()
        item.id = newDocument.id
        return newDocument.setAsync(mapper.mapToDataModel(item))
            .mapErrors { error -> mapFirestoreDetailedError(error, item.id) }
            .toSingleDefault(item.id)
    }

    fun addAll(items: List<Entity>): Single<List<String>> {
        val dataModels = items.map(mapper::mapToDataModel)
        val documents = HashMap<String, FirestoreModel>()
        dataModels.forEachIndexed { index, firestoreModel ->
            documents[items[index].id] = firestoreModel
        }
        return firebaseFirestore.collection(collectionName)
            .addAsync(documents)
            .mapErrors { error -> mapFirestoreDetailedError(error, "add list") }
    }

    fun update(item: Entity): Completable {
        return firebaseFirestore.collection(collectionName).document(item.id)
            .setAsync(mapper.mapToDataModel(item))
            .mapErrors { error -> mapFirestoreDetailedError(error, item.id) }
    }


    fun get(id: String): Single<Entity> =
        firebaseFirestore.collection(collectionName)
            .document(id)
            .getAsync(firestoreModelClass)
            .map(mapper::mapToEntity)
            .mapErrors { error -> mapFirestoreDetailedError(error, id) }

    fun getNullable(id: String): Maybe<Entity> =
        firebaseFirestore.collection(collectionName)
            .document(id)
            .getAsyncMaybe(firestoreModelClass)
            .map(mapper::mapToEntity)
            .mapErrors { error -> mapFirestoreDetailedError(error, id) }

    fun observe(id: String): Observable<Entity> =
        firebaseFirestore.collection(collectionName)
            .document(id)
            .observe(firestoreModelClass)
            .map(mapper::mapToEntity)
            .mapErrors { error -> mapFirestoreDetailedError(error, id) }

    fun observe(): Observable<List<Entity>> =
        firebaseFirestore.collection(collectionName)
            .observe(firestoreModelClass)
            .map { list -> list.map(mapper::mapToEntity) }
            .mapErrors { error -> mapFirestoreDetailedError(error, "observe all") }

    fun getAll(): Single<List<Entity>> =
        firebaseFirestore.collection(collectionName)
            .getAll(firestoreModelClass)
            .map { list -> list.map(mapper::mapToEntity) }
            .mapErrors { error -> mapFirestoreDetailedError(error, "get all") }

    private fun mapFirestoreDetailedError(mainError: Throwable, documentId: String) =
        if (mainError is FirestoreError) {
            mainError
        } else {
            DetailedFirestoreError(mainError, collectionName, documentId)
        }
}