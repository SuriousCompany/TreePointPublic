package company.surious.domain.use_case.base

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

abstract class SingleUseCase<Params, Result : Any> {
    protected abstract fun createSingle(params: Params): Single<Result>

    protected open fun applySchedulers(single: Single<Result>): Single<Result> =
        single.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

    fun execute(params: Params): Single<Result> = applySchedulers(createSingle(params))
}