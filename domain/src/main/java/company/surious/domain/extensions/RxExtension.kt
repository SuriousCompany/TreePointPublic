package company.surious.domain.extensions

import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.disposables.Disposable

//Observable:
fun ObservableEmitter<*>.isActive() = !isDisposed

fun <T : Any> ObservableEmitter<T>.safeOnNext(item: T) {
    if (isActive()) {
        onNext(item)
    }
}

fun ObservableEmitter<*>.safeOnError(error: Throwable) {
    if (isActive()) {
        onError(error)
    }
}

fun ObservableEmitter<*>.safeOnComplete() {
    if (isActive()) {
        onComplete()
    }
}

fun <T : Any> Observable<T>.emitEverythingTo(emitter: ObservableEmitter<T>): Disposable =
    subscribe(
        {
            emitter.safeOnNext(it)
        },
        {
            emitter.safeOnError(it)
        },
        {
            emitter.safeOnComplete()
        })

fun <T : Any> Observable<T>.mapErrors(function: (Throwable) -> Throwable) =
    onErrorResumeNext { error -> Observable.error(function.invoke(error)) }

fun <T : Any> Observable<T>.mapErrors(error: Throwable) =
    onErrorResumeNext { Observable.error(error) }

//Single:
fun SingleEmitter<*>.isActive() = !isDisposed

fun <T : Any> SingleEmitter<T>.safeOnSuccess(item: T) {
    if (isActive()) {
        onSuccess(item)
    }
}

fun SingleEmitter<*>.safeOnError(error: Throwable) {
    if (isActive()) {
        onError(error)
    }
}

fun <T : Any> Single<T>.mapErrors(function: (Throwable) -> Throwable) =
    onErrorResumeNext { error -> Single.error(function.invoke(error)) }

fun <T : Any> Single<T>.mapErrors(error: Throwable) =
    onErrorResumeNext { Single.error(error) }

//Maybe:
fun MaybeEmitter<*>.isActive() = !isDisposed

fun <T : Any> MaybeEmitter<T>.safeOnSuccess(item: T) {
    if (isActive()) {
        onSuccess(item)
    }
}

fun MaybeEmitter<*>.safeOnComplete() {
    if (isActive()) {
        onComplete()
    }
}

fun MaybeEmitter<*>.safeOnError(error: Throwable) {
    if (isActive()) {
        onError(error)
    }
}

fun <T : Any> Maybe<T>.toSingle(error: Throwable) =
    Single.create<T> { emitter ->
        emitter.setDisposable(subscribe(
            emitter::safeOnSuccess,
            emitter::safeOnError
        ) {
            emitter.safeOnError(error)
        })
    }

fun <T : Any> Maybe<T>.mapErrors(function: (Throwable) -> Throwable) =
    onErrorResumeNext { error -> Maybe.error(function.invoke(error)) }

fun <T : Any> Maybe<T>.mapErrors(error: Throwable) =
    onErrorResumeNext { Maybe.error(error) }


//Completable
fun CompletableEmitter.isActive() = !isDisposed

fun CompletableEmitter.safeOnComplete() {
    if (isActive()) {
        onComplete()
    }
}

fun CompletableEmitter.safeOnError(error: Throwable) {
    if (isActive()) {
        onError(error)
    }
}

fun Completable.mapErrors(function: (Throwable) -> Throwable) =
    onErrorResumeNext { error -> Completable.error(function.invoke(error)) }

fun Completable.mapErrors(error: Throwable) =
    onErrorResumeNext { Completable.error(error) }