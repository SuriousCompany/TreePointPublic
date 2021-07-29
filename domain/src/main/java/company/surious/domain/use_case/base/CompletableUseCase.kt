package company.surious.domain.use_case.base

import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

abstract class CompletableUseCase<Params> {

    protected abstract fun createCompletable(params: Params): Completable

    protected fun applySchedulers(completable: Completable) =
        completable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

    fun execute(params: Params) = applySchedulers(createCompletable(params))
}
