package company.surious.domain.use_case.base

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

abstract class ObservableUseCase<Params, Result> {

    protected abstract fun createObservable(params: Params): Observable<Result>

    protected fun applySchedulers(observable: Observable<Result>) =
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

    fun execute(params: Params) = applySchedulers(createObservable(params))
}