package company.surious.domain.use_case.base

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.schedulers.Schedulers

abstract class MaybeUseCase<Params, Result> {
    protected abstract fun createMaybe(params: Params): Maybe<Result>

    protected fun applySchedulers(maybe: Maybe<Result>) =
        maybe.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

    fun execute(params: Params) = applySchedulers(createMaybe(params))
}