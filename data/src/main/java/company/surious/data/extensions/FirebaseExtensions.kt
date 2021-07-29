package company.surious.data.extensions

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import company.surious.domain.errors.NoFirestoreDocumentError
import company.surious.domain.extensions.safeOnComplete
import company.surious.domain.extensions.safeOnError
import company.surious.domain.extensions.safeOnNext
import company.surious.domain.extensions.safeOnSuccess
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.Disposables
import kotlin.reflect.KClass

fun DocumentReference.getAsync() = Single.create<DocumentSnapshot> { emitter ->
    get()
        .addOnSuccessListener(emitter::safeOnSuccess)
        .addOnFailureListener(emitter::safeOnError)
}

fun <T : Any> DocumentReference.getAsync(clazz: KClass<T>) = Single.create<T> { emitter ->
    get()
        .addOnSuccessListener { document ->
            try {
                val t = document.toObject(clazz.java)
                if (t != null) {
                    emitter.safeOnSuccess(t)
                } else {
                    emitter.safeOnError(NoFirestoreDocumentError(clazz.simpleName!!, id))
                }
            } catch (error: Throwable) {
                emitter.safeOnError(error)
            }
        }
        .addOnFailureListener(emitter::safeOnError)
}

fun <T : Any> DocumentReference.getAsyncMaybe(clazz: KClass<T>) = Maybe.create<T> { emitter ->
    get()
        .addOnSuccessListener { document ->
            try {
                val t = document.toObject(clazz.java)
                if (t != null) {
                    emitter.safeOnSuccess(t)
                } else {
                    emitter.safeOnComplete()
                }
            } catch (error: Throwable) {
                emitter.safeOnError(error)
            }
        }
        .addOnFailureListener(emitter::safeOnError)
}

fun <T : Any> DocumentReference.setAsync(t: T) = Completable.create { emitter ->
    set(t)
        .addOnSuccessListener { emitter.safeOnComplete() }
        .addOnFailureListener(emitter::safeOnError)
}

fun <T : Any> DocumentReference.observe(clazz: KClass<T>) =
    Observable.create<T> { emitter ->
        val listener = addSnapshotListener { value, error ->
            if (error == null) {
                try {
                    val response = value?.toObject(clazz.java)
                    if (response != null) {
                        emitter.safeOnNext(response)
                    } else {
                        emitter.safeOnError(NoFirestoreDocumentError(clazz.simpleName!!, id))
                    }
                } catch (error: Throwable) {
                    emitter.safeOnError(error)
                }
            } else {
                emitter.safeOnError(error)
            }
        }
        emitter.setDisposable(Disposables.fromAction { listener.remove() })
    }

fun <T : Any> CollectionReference.getAll(clazz: KClass<T>) =
    Single.create<List<T>> { emitter ->
        get()
            .addOnSuccessListener { snapshot ->
                if (!snapshot.isEmpty) {
                    try {
                        val mappedCollection =
                            snapshot.documents.mapNotNull { it.toObject(clazz.java) }
                        emitter.safeOnSuccess(mappedCollection)
                    } catch (error: Throwable) {
                        emitter.safeOnError(error)
                    }
                } else {
                    emitter.safeOnSuccess(emptyList())
                }
            }
            .addOnFailureListener(emitter::safeOnError)
    }

fun <T : Any> CollectionReference.observe(clazz: KClass<T>) =
    Observable.create<List<T>> { emitter ->
        val listener = addSnapshotListener { value, error ->
            if (error == null) {
                try {
                    if (value != null) {
                        val mappedDocuments = value.documents.mapNotNull {
                            it.toObject(clazz.java)
                        }
                        emitter.safeOnNext(mappedDocuments)
                    }
                } catch (error: Throwable) {
                    emitter.safeOnError(error)
                }
            } else {
                emitter.safeOnError(error)
            }
        }
        emitter.setDisposable(Disposables.fromAction { listener.remove() })
    }

