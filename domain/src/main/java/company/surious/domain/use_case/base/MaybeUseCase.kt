package company.surious.domain.use_case.base

import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

abstract class MaybeUseCase<Params, Result> {
    protected abstract fun createMaybe(params: Params): Maybe<Result>

    protected fun applySchedulers(maybe: Maybe<Result>) =
        maybe.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

    fun execute(params: Params) = applySchedulers(createMaybe(params))
}